package com.example.wasla.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;

import com.example.wasla.AppConfig.Cods;
import com.example.wasla.AppConfig.SharedPreference;
import com.example.wasla.Helpers.View.FTH;
import com.example.wasla.R;
import com.example.wasla.UI.Fragments.UserAuth.Auth.AuthFragment;
import com.example.wasla.UI.Fragments.UserAuth.passwordConfirm.ChangePasswordFragment;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class AuthActivity extends AppCompatActivity {

    @BindView(R.id.user_auth_container_view)
    FragmentContainerView userAuthContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind(this);

        if (SharedPreference.IS_THERE_COD())
            FTH.replaceFragment(Cods.Auth_C, this, new ChangePasswordFragment());
        else
            FTH.replaceFragment(Cods.Auth_C, this, new AuthFragment());
    }
}