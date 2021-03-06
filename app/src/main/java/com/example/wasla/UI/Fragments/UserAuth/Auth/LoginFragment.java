package com.example.wasla.UI.Fragments.UserAuth.Auth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.wasla.Helpers.View.FTH;
import com.example.wasla.Models.Request.LoginRequest;
import com.example.wasla.Models.Response.LoginResponse;
import com.example.wasla.R;
import com.example.wasla.UI.Activities.SecondActivity;
import com.example.wasla.UI.Fragments.UserAuth.passwordConfirm.ForgetPasswordFragment;
import com.example.wasla.ViewModels.AuthViewModel;
import com.example.wasla.ViewModels.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wasla.AppConfig.Cods.Auth_C;
import static com.example.wasla.AppConfig.Cods._963;
import static com.example.wasla.AppConfig.SharedPreference.CASH_ACCESS_TOKEN;
import static com.example.wasla.AppConfig.SharedPreference.GET_FIRE_TOKEN;
import static com.example.wasla.Helpers.Converters.ConvertPhoneNumber.convertPhoneNumberToRegular;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_ERROR;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_NO_INTERNET;

@SuppressLint("NonConstantResourceId")
public class LoginFragment extends Fragment {

    @BindView(R.id.login_btn)
    AppCompatButton loginBtn;
    @BindView(R.id.phone_number)
    TextInputEditText phoneNumber;
    @BindView(R.id.login_password)
    TextInputEditText password;
    @BindView(R.id.forget_pass)
    TextView forgetPass;
    @BindView(R.id.licens)
    TextView license;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);


        listeners();

        return view;
    }

    void listeners() {
        //?????????? ????????????
        loginBtn.setOnClickListener(onLoginClickListener);
        //???????? ???????? ????????????
        forgetPass.setOnClickListener(onForgetPassClicked);
    }

// ___ observers _____________________________________________________________

    //?????????? ?????????? ????????????
    private final Observer<Pair<LoginResponse, String>> loginObserver = pair -> {

        if (pair != null) {
            if (pair.first != null) {

                if (!pair.first.result.userTrip.equals("driver")) {
                    TOAST_ERROR(requireContext(), "???????????? ???????????? ?????? ???????? ???????? ");
                    enable(loginBtn);
                    return;
                }

                //?????????? ??????????????
                CASH_ACCESS_TOKEN(pair.first);

                if (!GET_FIRE_TOKEN().isEmpty())
                    UserViewModel.getInstance().postFirToken(GET_FIRE_TOKEN());
                //?????? ?????????????? ????????????????
                UserViewModel.getInstance().getUserInfo(pair.first.result.userId);

                //?????????? ?????????????? ????????????????
                startSecond();


            } else {

                enable(loginBtn);

                password.setError(pair.second);
                Toast.makeText(getContext(), pair.second, Toast.LENGTH_SHORT).show();
            }
        } else {
            enable(loginBtn);
            TOAST_NO_INTERNET(getContext());

        }
    };

// ___ onClick Listeners  ____________________________________________________

    //?????????? ????????????
    private final View.OnClickListener onLoginClickListener = v -> {
        if (checkFields()) {

            desAble(loginBtn);

            AuthViewModel.getInstance().login(getLoginObject());
            AuthViewModel.getInstance().loginLiveData.observe(requireActivity(), loginObserver);
        }
    };
    //???????? ???????? ????????????
    private final View.OnClickListener onForgetPassClicked = v -> {
        FTH.addFragmentUpFragment(Auth_C, requireActivity(), new ForgetPasswordFragment(),"");
    };

// ___ checking  _____________________________________________________________

    /**
     * checking if all fields its ok
     */
    boolean checkFields() {
        if (Objects.requireNonNull(phoneNumber.getText()).toString().isEmpty()
                || Objects.requireNonNull(phoneNumber.getText()).toString().length() < 9) {
            phoneNumber.setError("???????? ???? ?????? ???????????? ");
        }
        if (Objects.requireNonNull(password.getText()).toString().isEmpty()) {
            password.setError("?????? ?????????? ???????? ???????? ????????????????");
        }

        return !phoneNumber.getText().toString().isEmpty() && !password.getText().toString().isEmpty();
    }

    /**
     * get login object to send to api
     */
    private LoginRequest getLoginObject() {
        return new LoginRequest(convertPhone(), Objects.requireNonNull(password.getText()).toString());
    }

    /**
     * ?????????? ?????? ???????????? ???????????? ????????????????  ???????? :<br>
     *  +963923456789 ||00963923456789  ||  0923456789  ||  923456789 <br> ===>> ( 923456789 )
     *
     */
    private String convertPhone() {

        String phone = Objects.requireNonNull(phoneNumber.getText()).toString();

        //?????????? ?????? ???????????? ???????????? ????????????????  ???????? :
        // +963923456789 ||00963923456789  ||  0923456789  ||  923456789 ===>> ( 923456789 )
        phone = convertPhoneNumberToRegular(phone);

        // ?????????? ?????? ?????????? ??????????
        return _963 + phone;

    }


    void enable(View view) {
        view.setEnabled(true);
    }

    void desAble(View view) {
        view.setEnabled(false);
    }


    /**
     * start activity {@link SecondActivity}
     */
    void startSecond() {


        startActivity(new Intent(requireActivity(), SecondActivity.class));
        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        requireActivity().finish();
    }

    private void removeObservers() {
        if (AuthViewModel.getInstance().loginLiveData != null)
            AuthViewModel.getInstance().loginLiveData.removeObserver(loginObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeObservers();
    }


}