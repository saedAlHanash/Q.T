package com.example.wasla.UI.Fragments.UserAuth.Auth;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.wasla.AppConfig.Cods;
import com.example.wasla.Helpers.View.FTH;
import com.example.wasla.R;

import butterknife.BindDimen;
import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class AuthFragment extends Fragment {

    @BindDimen(R.dimen._30sdp)
    int _30sdp;

    @BindView(R.id.logo)
    ImageView logo;

    @BindView(R.id.login_btn)
    AppCompatButton loginBtn;
    @BindView(R.id.terms_and_privacy)
    TextView termsAndPrivacy;
    @BindView(R.id.underlogo1)
    LinearLayout underLogo1;


    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auth, container, false);
        bind(this, view);

        initAnimation();

        listeners();

        return view;
    }

    /**
     * تهيئة الأنميشن
     */
    void initAnimation() {
        new Handler().postDelayed(this::allAnimation, 500);
    }

    /**
     * كل الأنميشن في العناصر
     */
    void allAnimation() {
        Animation slide_up = AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_up);
        slide_up.setDuration(700);

        logo.animate().translationY(_30sdp / 4f).setDuration(500);
        new Handler().postDelayed(() -> {
            logo.animate().translationY(-1 * _30sdp).setDuration(500);
        }, 500);


        new Handler().postDelayed(() -> {

            loginBtn.setVisibility(View.VISIBLE);
            underLogo1.setVisibility(View.VISIBLE);

            loginBtn.startAnimation(slide_up);
            underLogo1.startAnimation(slide_up);


        }, 700);
    }

    void listeners() {
        loginBtn.setOnClickListener(onLoginClickListener);
    }

    private final View.OnClickListener onLoginClickListener = v -> {
        FTH.replaceFragment(Cods.Auth_C, requireActivity(), new LoginFragment());
    };
}