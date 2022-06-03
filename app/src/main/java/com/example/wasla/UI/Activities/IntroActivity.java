package com.example.wasla.UI.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.wasla.AppConfig.SharedPreference;
import com.example.wasla.R;

import java.util.ArrayList;

import butterknife.BindDimen;
import butterknife.BindView;

import static butterknife.ButterKnife.bind;
import static com.example.wasla.AppConfig.Cods.HI_LOTTIE_ANIMATION;
import static com.example.wasla.AppConfig.Cods.LOCATION_MARKER_LOTTIE_ANIMATION;
import static com.example.wasla.Helpers.system.ScreenHelper.hideStatusBar;

public class IntroActivity extends AppCompatActivity {

    @BindView(R.id.skip_btn)
    AppCompatButton skipBtn;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.under_logo)
    LinearLayout underLogo;
    @BindDimen(R.dimen._180sdp)
    int _180sdp;


    int i = 0;
    Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        bind(this);
        hideStatusBar(this);

        listeners();
        YoYo.with(Techniques.SlideInDown).duration(500).playOn(textView);
        YoYo.with(Techniques.SlideInUp).delay(1000).duration(500).playOn(underLogo);


    }

    void listeners() {
        skipBtn.setOnClickListener(view -> {

            SharedPreference.CASH_SHOW_INTRO();

            startMain();

        });
    }

    /**
     * start activity {@link AuthActivity} and finish current
     */
    void startMain() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        this.finish();
    }
}