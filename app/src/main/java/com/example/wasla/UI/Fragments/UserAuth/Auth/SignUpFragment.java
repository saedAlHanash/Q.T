package com.example.wasla.UI.Fragments.UserAuth.Auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.wasla.Helpers.Converters.ConvertPhoneNumber;
import com.example.wasla.R;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class SignUpFragment extends Fragment {

    //region GLOBAL_VAR

    @BindView(R.id.login_tv)
    TextView loginTv;

    @BindView(R.id.user_name)
    TextInputEditText firstName;
    @BindView(R.id.sure_name)
    TextInputEditText sureName;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.re_password)
    TextInputEditText rePassword;
    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.phone_number)
    TextInputEditText phoneNumber;
    @BindView(R.id.sing_up_btn)
    AppCompatButton signUpButton;


    @BindView(R.id.first_name_textlayout)
    TextInputLayout firstNameTextLayout;
    @BindView(R.id.phoneNumber_outLine)
    TextInputLayout phoneNumberOutLine;
    @BindView(R.id.last_name_textlayout)
    TextInputLayout lastNameTextLayout;
    @BindView(R.id.password_textlayout)
    TextInputLayout passwordTextLayout;
    @BindView(R.id.repassword_textlayout)
    TextInputLayout rePasswordTextLayout;

    View view;

    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        listeners();

        return view;
    }


    void listeners() {
        //?????????? ????????????
        loginTv.setOnClickListener(onLoginClicked);
        //???? ?????????? ????????????
        signUpButton.setOnClickListener(onSignUpClicked);
        //?????????? ?????? ???????????????? ?????? ?????????? ???? ??????????????
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                isOpen -> restErrors());
    }

    /**
     * send request signUp to api
     */
    void sendRequestApi() {

    }

    void observingResponse() {

    }

    //?????????? ????????????
    private final View.OnClickListener onLoginClicked = v -> {

    };

    //???? ?????????? ????????????
    private final View.OnClickListener onSignUpClicked = v -> {

    };

    /**
     * rest all outline fields error
     */
    void restErrors() {
        //?????????? ??????????
        if (firstNameTextLayout.isErrorEnabled())
            firstNameTextLayout.setErrorEnabled(false);
        // ?????????? ????????????
        if (lastNameTextLayout.isErrorEnabled())
            lastNameTextLayout.setErrorEnabled(false);
        // ???????? ????????????
        if (passwordTextLayout.isErrorEnabled())
            passwordTextLayout.setErrorEnabled(false);
        // ?????????? ???????? ????????????
        if (rePasswordTextLayout.isErrorEnabled())
            rePasswordTextLayout.setErrorEnabled(false);
        // ?????? ????????????
        if (phoneNumberOutLine.isErrorEnabled())
            phoneNumberOutLine.setErrorEnabled(false);


    }

    /**
     * checking if all fields is valid
     */
    private boolean checkFields() {
        //???????? ???? ?????????? ??????????
        if (firstName.getText().length() == 0) {
            firstNameTextLayout.setError("??????????");
            return false;
        }
        // ???????? ???? ?????????? ????????????
        if (sureName.getText().length() == 0) {
            lastNameTextLayout.setError("??????????");
            return false;
        }
        // ???????? ???? ???????? ????????????
        if (password.getText().length() == 0) {
            passwordTextLayout.setError("??????????");
            return false;
        }
        // ???????? ???? ?????????? ???????? ????????????
        if (rePassword.getText().length() == 0) {
            rePasswordTextLayout.setError("??????????");
            return false;
        }
        // ???????? ???? ?????????? ???????? ???????????? ???? ??????????????
        if (!rePassword.getText().equals(password.getText())) {
            rePasswordTextLayout.setError("???????? ???????????? ?????? ????????????");
            return false;
        }
        // ???????? ???? ?????? ????????????
        if (Objects.requireNonNull(phoneNumber.getText()).toString().isEmpty()
                || Objects.requireNonNull(phoneNumber.getText()).toString().length() < 9) {
            phoneNumberOutLine.setError("?????? ???????????? ???????????? ?????? ????????");
            return false;
        }

        return true;
    }

}