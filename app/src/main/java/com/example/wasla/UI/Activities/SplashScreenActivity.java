package com.example.wasla.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.wasla.Models.Response.AvailableModel;
import com.example.wasla.Models.Response.BalanceOfToday;
import com.example.wasla.R;
import com.example.wasla.UI.MainActivity;
import com.example.wasla.ViewModels.DriverViewModel;
import com.example.wasla.ViewModels.UserViewModel;

import static com.example.wasla.AppConfig.Cods.LAT;
import static com.example.wasla.AppConfig.Cods.LNG;
import static com.example.wasla.AppConfig.SharedPreference.GET_MY_ID;
import static com.example.wasla.AppConfig.SharedPreference.IS_SHOW_INTRO;
import static com.example.wasla.AppConfig.SharedPreference.IS_THERE_ACCESS_TOKEN;
import static com.example.wasla.AppConfig.SharedPreference.IS_THERE_TRIP;
import static com.example.wasla.AppConfig.SharedPreference.getInstance;
import static com.example.wasla.Helpers.View.NoteMessage.*;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_ERROR;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_NO_INTERNET;

public class SplashScreenActivity extends AppCompatActivity {

    DriverViewModel driver = DriverViewModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // SharedPreference تهيئة ال
        getInstance(this);

//        //اذا كان المستخدم سجل الدخول
//        if (IS_THERE_ACCESS_TOKEN()) {
//            checkStickyTrip();
//        } else
            initLottie();
    }

// ___ data from api ______________________________________________________________________

    private void mackDriverAvailable() {
        DriverViewModel.getInstance().makeDriverAvailable();
        DriverViewModel.getInstance().availableDriverLiveData.observe(this, observerMakeDriver);
    }

    /**
     * طلب المعلومات اليومية للسائق من السيرفر
     */
    void getDayInfoFromApi() {
        driver.getDayInfo(driver.dayInfoLiveData == null);
        driver.dayInfoLiveData.observe(this, observer);
    }

    /**
     * يتحقق من وجود رحلة سابقة ويتخذ الاجراء المناسب
     */
    void checkStickyTrip() {
        if (IS_THERE_TRIP())
            mackDriverAvailable();
        else
            initLottie();
    }

//__ Observers___________________________________________________________________________

    //راقب اتاحةالسائق
    private final Observer<Pair<AvailableModel, String>> observerMakeDriver = pair -> {

        if (pair != null) {
            if (pair.first != null) {
                Intent intent = new Intent(this, MainMapActivity.class);

                if (pair.first.result != null) {
                    intent.putExtra(LAT, pair.first.result.lat);
                    intent.putExtra(LNG, pair.first.result.lng);
                }

                startActivity(intent);

                this.finish();

            } else
                TOAST_ERROR(this, pair.second);
        }
        else
        TOAST_NO_INTERNET(this);

    };

    //مراقب المعلومات اليومية للسائق
    private final Observer<Pair<BalanceOfToday, String>> observer = pair -> {
        if (pair != null) {
            if (pair.first != null) {
                removeObservers();
                startSecond();
            } else
                TOAST_ERROR(com.example.wasla.UI.Activities.SplashScreenActivity.this, pair.second);
        } else
            TOAST_NO_INTERNET(com.example.wasla.UI.Activities.SplashScreenActivity.this);
    };

// ___ animation and lottie _______________________________________________________________

    /**
     * تهيئة الأنميشن في شاشة البداية
     */
    void initLottie() {
        new Handler().postDelayed(this::startIntro, 1000);
    }

    /**
     * عند انتهاء الأنميشن في شاشة البداية
     */
    void onLottieFinished() {

        //(1) عند وجود توكين شغل الواجهة الرئيسية فورا
        // (2) وإلا تحقق من أن المستخدم شاهد واجهة ال intro
        // اذا شاهدها شغل ال mainActivity
        //وإلا شغل ال intro

        if (IS_THERE_ACCESS_TOKEN())
            getDayInfoFromApi();

        else {
            if (!IS_SHOW_INTRO())
                startIntro();
            else
                startMain();
        }
    }

    /**
     * جلب البيانات الحيوية في الشاشة الأولى للسرعة
     */
    void initBastData() {
        if (IS_THERE_ACCESS_TOKEN()) {

            //طلب معلومات المحفظة
            DriverViewModel.getInstance().getMyBalance();
            //طلب معلومات الرحلات السابقة
            DriverViewModel.getInstance().getOldTrips(true);
            //طلب معلومات الرحلات المجدولة
            DriverViewModel.getInstance().getScheduledTrips(true);
            //طلب معلومات المستخدم
            UserViewModel.getInstance().getUserInfo(GET_MY_ID());

        }
    }

// ___ start or finishing _________________________________________________________________

    /**
     * start activity {@link IntroActivity} and finish current
     */
    void startIntro() {
        startActivity(new Intent(this, IntroActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        this.finish();
    }

    /**
     * start activity {@link SecondActivity} and finish current
     */
    void startSecond() {
        startActivity(new Intent(this, SecondActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        this.finish();
    }

    /**
     * start activity {@link MainActivity} and finish current
     */
    void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        this.finish();
    }

    private void removeObservers() {

        if (DriverViewModel.getInstance().availableDriverLiveData != null)
            DriverViewModel.getInstance().availableDriverLiveData.removeObserver(observerMakeDriver);

        if (driver.dayInfoLiveData != null)
            driver.dayInfoLiveData.removeObserver(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeObservers();
    }
}