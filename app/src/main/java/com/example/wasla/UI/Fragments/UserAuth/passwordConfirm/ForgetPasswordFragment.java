package com.example.wasla.UI.Fragments.UserAuth.passwordConfirm;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.wasla.AppConfig.Cods;
import com.example.wasla.Helpers.View.FTH;
import com.example.wasla.R;
import com.example.wasla.ViewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wasla.AppConfig.Cods._963;
import static com.example.wasla.AppConfig.SharedPreference.CASH_COD_STAT;
import static com.example.wasla.AppConfig.SharedPreference.CASH_PHONE_FORGET_PASS;
import static com.example.wasla.AppConfig.SharedPreference.GET_FORGET_PASS_PHONE;
import static com.example.wasla.AppConfig.SharedPreference.REMOVE_TIMER;
import static com.example.wasla.Helpers.Converters.ConvertPhoneNumber.convertPhoneNumberToRegular;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_ERROR;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_NO_INTERNET;

;


public class ForgetPasswordFragment extends Fragment {

    View view;

    @BindView(R.id.phone_number)
    TextInputEditText phoneNumber;

    @BindView(R.id.continue_btn)
    AppCompatButton continueBtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        ButterKnife.bind(this, view);


        listeners();
        return view;
    }

    void listeners() {
        continueBtn.setOnClickListener(listener);
    }

    // مراقب المتابعة
    private final Observer<Pair<Boolean, String>> observerForgetPass = pair -> {

        continueBtn.setEnabled(true);

        if (pair != null) {
            if (pair.first) {
                goToNext();

            } else
                TOAST_ERROR(getContext(), pair.second);
        } else
            TOAST_NO_INTERNET(getContext());
    };

    //زر المتابعة
    private final View.OnClickListener listener = v -> {

        continueBtn.setEnabled(false);

        if (checkFields()) {

            String phone = phoneNumber.getText().toString();

            if (checkPhoneNumberLength(phone))
                //تحويل رقم الهاتف للصيغة النظامية  مثال :
                // +963923456789 ||00963923456789  ||  0923456789  ||  923456789 ===>> ( 923456789 )
                phone = convertPhoneNumberToRegular(phone);
            else {
                continueBtn.setEnabled(true);
                phoneNumber.setError("خطأ في رقم الهاتف");
            }

            if (checkPhoneAfterConvert(phone)) {

                // إضافة رمز البلد للرقم
                String p = _963 + phone;

                // التحقق من أن الهاتف المدخل الجديد كان قد تم ادخاله من قب وطلب الرسالة له
                if (p.equals(GET_FORGET_PASS_PHONE())) {
                    goToNext();
                    return;
                }

                //تخزين الهاتف الجديد
                CASH_PHONE_FORGET_PASS(p);

                // مسح المؤقت
                REMOVE_TIMER();

                // طلب ارسال الرسالة من api
                AuthViewModel.getInstance().forgetPassWord(p);
                AuthViewModel.getInstance().forgetPassLiveData.observe(requireActivity(), observerForgetPass);


            } else {
                continueBtn.setEnabled(true);
                phoneNumber.setError("خطأ في رقم الهاتف");
            }
        } else {
            continueBtn.setEnabled(true);
            phoneNumber.setError("خطأ في رقم الهاتف");
        }

    };


    boolean checkFields() {
        if (phoneNumber.getText().toString().isEmpty()) {
            phoneNumber.setError("يجب ادخال رقم الهاتف للمتابعة");
        }

        return !phoneNumber.getText().toString().isEmpty();
    }

    boolean checkPhoneNumberLength(String phone) {
        return phone.length() >= 9;
    }

    boolean checkPhoneAfterConvert(String phone) {
        return phone.length() == 9;
    }

    void goToNext() {

        CASH_COD_STAT();

        FTH.replaceFragment(Cods.Auth_C, requireActivity(), new ChangePasswordFragment());
    }
}