package com.example.wasla.UI.Fragments.UserAuth.passwordConfirm;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.wasla.AppConfig.Cods;
import com.example.wasla.Helpers.Converters.DateConverter;
import com.example.wasla.Helpers.View.Counter;
import com.example.wasla.Helpers.View.FTH;
import com.example.wasla.R;
import com.example.wasla.UI.Fragments.UserAuth.Auth.LoginFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wasla.AppConfig.Cods.Auth_C;
import static com.example.wasla.AppConfig.SharedPreference.CASH_TIMER;
import static com.example.wasla.AppConfig.SharedPreference.GET_TIMER;
import static com.example.wasla.AppConfig.SharedPreference.IS_THERE_TIMER;
import static com.example.wasla.AppConfig.SharedPreference.REMOVE_COD_STAT;
import static com.example.wasla.AppConfig.SharedPreference.REMOVE_TIMER;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_NO_INTERNET;


public class ChangePasswordFragment extends Fragment {

    View view;

    @BindView(R.id.cod)
    TextInputEditText cod;
    @BindView(R.id.password)
    TextInputEditText password;


    @BindView(R.id.change_password_btn)
    AppCompatButton changePasswordBtn;
    @BindView(R.id.resend_cod)
    TextView resendCod;
    @BindView(R.id.counter)
    TextView counter;
    @BindView(R.id.login)
    TextView login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.bind(this, view);
        listeners();

        checkTimer();

        return view;
    }

    void listeners() {

        // متابعة
        changePasswordBtn.setOnClickListener(listener);

        // اعادة الارسال
        resendCod.setOnClickListener(reSendCodListener);

        //تسجيل الدخول
        login.setOnClickListener(loginListener);

    }

    boolean checkFields() {

        if (password.getText().toString().isEmpty()) {
            cod.setError("يجب ادخال كلمة مرور جديدة للمتابعة");
        }
        if (cod.getText().toString().isEmpty()) {
            password.setError("يجب ادخال رمز التحقق للمتابعة");
        } else if (cod.getText().toString().length() != 6) {
            cod.setError("يرجى ادخال رمز التحقق بشكل صحيح");
        }

        return cod.getText().toString().length() == 6 && !password.getText().toString().isEmpty();
    }


    //مراقب المتابعة بعد الادخال الصحيح
    private final Observer<Pair<Boolean, String>> observer = pair -> {
        if (pair != null) {
            if (pair.first) {
                REMOVE_COD_STAT();

                FTH.replaceFragment(Cods.Auth_C, requireActivity(), new LoginFragment());


            } else if (pair.second.contains("must")) {
                {
                    password.setError("يرجى أختيار مزيج من الأحرف الصغيرة والكبيرة ورمز واحد على الأقل");
                }
            } else {
                cod.setError("الرمز المدخل غير صحيح");
            }
        } else
            TOAST_NO_INTERNET(getContext());
    };

    //زر المتابة
    private final View.OnClickListener listener = v -> {


        FTH.popAllStack(requireActivity());
        FTH.replaceFragment(Cods.Auth_C, requireActivity(), new LoginFragment());


//        if (checkFields()) {
//
//            String pass = Objects.requireNonNull(this.password.getText()).toString();
//            String cod = Objects.requireNonNull(this.cod.getText()).toString();
//
//
//            AuthViewModel.getInstance().changePassword(new ChangePassRequest(cod, pass));
//            AuthViewModel.getInstance().changePassLiveData.observe(requireActivity(), observer);
//        }
    };

    // اعادة ارسال الرمز
    private final View.OnClickListener reSendCodListener = v -> {

        CASH_TIMER();
        checkTimer();

//        AuthViewModel.getInstance().resendCod(GET_FORGET_PASS_PHONE());
//
//        AuthViewModel.getInstance().resendLiveData.observe(requireActivity(), pair -> {
//            if (pair != null) {
//
//                CASH_TIMER();
//                checkTimer();
//
//            } else {
//                resendCod.setVisibility(View.VISIBLE);
//            }
//
//        });


    };

    //زر تسجيل الدخول
    private final View.OnClickListener loginListener = v -> {
        REMOVE_COD_STAT();
        FTH.replaceFragment(Auth_C, requireActivity(), new LoginFragment());
    };


    void checkTimer() {
        if (IS_THERE_TIMER()) {

            long timer = DateConverter.findDifferenceMinutes(GET_TIMER(), DateConverter.dateToString(new Date()));

            if (timer > 0) {
                Counter.CountTime(counter, resendCod, timer * 1000);
            } else {
                REMOVE_TIMER();
            }
        }
    }

}