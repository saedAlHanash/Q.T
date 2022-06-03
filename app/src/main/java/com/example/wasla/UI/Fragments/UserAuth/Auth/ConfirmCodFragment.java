package com.example.wasla.UI.Fragments.UserAuth.Auth;

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

import com.example.wasla.AppConfig.SharedPreference;
import com.example.wasla.Helpers.Layouts.VerificationCodeView;
import com.example.wasla.Models.Response.LoginResponse;
import com.example.wasla.R;
import com.example.wasla.UI.Activities.SecondActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.wasla.AppConfig.SharedPreference.MY_ID;
import static com.example.wasla.AppConfig.SharedPreference.TOKEN;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_NO_INTERNET;

public class ConfirmCodFragment extends Fragment {
    View view;
    @BindView(R.id.phone_number_tv)
    TextView phoneNumberTv;
    @BindView(R.id.verificationCodeView)
    VerificationCodeView verificationCodeView;
    @BindView(R.id.confirm_cod_btn)
    AppCompatButton confirmCodBtn;
    @BindView(R.id.resend_cod_tv)
    TextView resendCod;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirm_cod, container, false);
        ButterKnife.bind(this, view);
        listeners();
        return view;
    }

    void listeners() {

        //زر تأكيد الكود
        confirmCodBtn.setOnClickListener(onConfirmClickListener);

        //زر اعادة ارسال الكود
        resendCod.setOnClickListener(onResendCodClickListener);

        //عند نغيير الكود (من شان تخلص من اللون الأحمر)
        verificationCodeView.setCodeCompleteListener(onchangeCod);

    }

    // متنصت على حال تأكيد الرقم
    private final Observer<Pair<LoginResponse, String>> observerConfirm = confirmBody -> {

        if (confirmBody != null) {
            if (confirmBody.first != null) {

                SharedPreference.spEdit.putString(TOKEN, confirmBody.first.result.accessToken);
                SharedPreference.spEdit.putInt(MY_ID, confirmBody.first.result.userId);
                SharedPreference.spEdit.apply();

                startSecond();

            } else {
                enable(confirmCodBtn);
                Toast.makeText(getContext(), confirmBody.second, Toast.LENGTH_SHORT).show();
                verificationCodeView.setCodeItemErrorLineDrawable();
            }
        } else {
            enable(confirmCodBtn);
            TOAST_NO_INTERNET(getContext());
        }
    };


    private final View.OnClickListener onConfirmClickListener = v -> {
        disable(confirmCodBtn);
        if (checkCod()) {
            //  AuthViewModel.getInstance().ConfirmCod(verificationCodeView.getTextString());
            // AuthViewModel.getInstance().confirmCodLiveData.observe(requireActivity(), observerConfirm);
        } else {
            enable(confirmCodBtn);
        }

    };
    private final View.OnClickListener onResendCodClickListener = v -> {
    };
    private final VerificationCodeView.CodeCompleteListener onchangeCod = complete -> {
        verificationCodeView.resetCodeItemLineDrawable();
    };

    /**
     * للتحقق من أن الكود مكتوب بالكامل أي أكبر من 4 أرقام
     *
     * @return true if cod length == 4 false if  cod length < 4
     */
    boolean checkCod() {
        return verificationCodeView.getTextString().length() < 4;
    }

    /**
     * start activity {@link SecondActivity}
     */
    void startSecond() {
        startActivity(new Intent(requireActivity(), SecondActivity.class));
        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        requireActivity().finish();
    }


    void enable(View view) {
        view.setEnabled(true);
    }

    void disable(View view) {
        view.setEnabled(false);
    }

}