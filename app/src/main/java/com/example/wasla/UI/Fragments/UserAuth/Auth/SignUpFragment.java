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
        //تسجبل الدخول
        loginTv.setOnClickListener(onLoginClicked);
        //زر انشاء الحساب
        signUpButton.setOnClickListener(onSignUpClicked);
        //متنصت على الكيبورد عند الفتح أو الإغلاق
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

    //تسجبل الدخول
    private final View.OnClickListener onLoginClicked = v -> {

    };

    //زر انشاء الحساب
    private final View.OnClickListener onSignUpClicked = v -> {

    };

    /**
     * rest all outline fields error
     */
    void restErrors() {
        //الاسم الأول
        if (firstNameTextLayout.isErrorEnabled())
            firstNameTextLayout.setErrorEnabled(false);
        // الاسم الأخير
        if (lastNameTextLayout.isErrorEnabled())
            lastNameTextLayout.setErrorEnabled(false);
        // كلمة المرور
        if (passwordTextLayout.isErrorEnabled())
            passwordTextLayout.setErrorEnabled(false);
        // تأكيد كلمة المرور
        if (rePasswordTextLayout.isErrorEnabled())
            rePasswordTextLayout.setErrorEnabled(false);
        // رقم الهاتف
        if (phoneNumberOutLine.isErrorEnabled())
            phoneNumberOutLine.setErrorEnabled(false);


    }

    /**
     * checking if all fields is valid
     */
    private boolean checkFields() {
        //تحقق من الاسم الأول
        if (firstName.getText().length() == 0) {
            firstNameTextLayout.setError("مطلوب");
            return false;
        }
        // تحقق من الاسم الأخير
        if (sureName.getText().length() == 0) {
            lastNameTextLayout.setError("مطلوب");
            return false;
        }
        // تحقق من كلمة المرور
        if (password.getText().length() == 0) {
            passwordTextLayout.setError("مطلوب");
            return false;
        }
        // تحقق من تأكيد كلمة المرور
        if (rePassword.getText().length() == 0) {
            rePasswordTextLayout.setError("مطلوب");
            return false;
        }
        // تحقق من تطابق كلمة المرور مع التأكيد
        if (!rePassword.getText().equals(password.getText())) {
            rePasswordTextLayout.setError("كلمة المرور غير مطابقة");
            return false;
        }
        // تحقق من رقم الهاتف
        if (Objects.requireNonNull(phoneNumber.getText()).toString().isEmpty()
                || Objects.requireNonNull(phoneNumber.getText()).toString().length() < 9) {
            phoneNumberOutLine.setError("رقم الهاتف المدخل غير صحيح");
            return false;
        }

        return true;
    }

}