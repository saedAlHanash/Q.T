package com.example.wasla.UI.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
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
    @BindView(R.id.imageView)
    ImageView image;
    @BindView(R.id.textView5)
    TextView underImage;
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

        new Handler(getMainLooper()).postDelayed(() -> {
            ImageViewAnimatedChange(this, image, R.drawable.taxi_delay, "متأخر ومافي مواصلات..؟");
        }, 4000);

        new Handler(getMainLooper()).postDelayed(() -> {
            ImageViewAnimatedChange(this, image, R.drawable.taxi_save_money, "حابب ترتاح وتوفر..؟");
        }, 8000);

        new Handler(getMainLooper()).postDelayed(() -> {
            ImageViewAnimatedChange(this, image, R.drawable.taxi_autopilot, "مالك غير Q.T");
            skipBtn.setText("التالي");
        }, 12000);





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

    public void ImageViewAnimatedChange(Context c,
                                        final ImageView v,
                                        final int new_image,
                                        String text) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setImageResource(new_image);
                underImage.setText(text);

                v.startAnimation(anim_in);
                underImage.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }
}