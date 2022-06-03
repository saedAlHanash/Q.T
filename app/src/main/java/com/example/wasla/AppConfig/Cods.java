package com.example.wasla.AppConfig;

import android.app.Activity;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;

import com.example.wasla.Models.BaseResponse;
import com.example.wasla.R;
import com.example.wasla.ViewModels.UserViewModel;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;

import static com.example.wasla.Helpers.View.NoteMessage.TOAST_NO_INTERNET;

public class Cods {


    public static Gson gson = new Gson();

// ___ REQUEST CODS  _____________________________________________________________

    public static final int REQUEST_CHECK_SETTINGS = 10001;
    public static final int REQUEST_CODE_CALL_PHONE = 11;
    public static final int REQUEST_CODE_GPS = 14;
    public static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 10;


// ___ lottie _____________________________________________________________

    public static final String DONE_LOTTIE_ANIMATION = "mochup_03_done.json";
    public static final String HI_LOTTIE_ANIMATION = "mochup_01_hi.json";
    public static final String LOCATION_MARKER_LOTTIE_ANIMATION = "mochup_02_location.json";

    public static final int Auth_C = R.id.user_auth_container_view;
//    public static final int SECOND_C = R.id.sc_fr_c;
//    public static final int MAIN_MAP_C = R.id.main_map_container;

    public static final int CHANGE_PASS_COD = 0;
    public static final int SING_UP_COD = 1;

// ___ fragments names  _____________________________________________________________

    public static final String EDIT_PROFILE_FRAGMENT = "f_e_p";
    public static final String PROFILE_FRAGMENT = "f_p";
    public static final String HAVE_ORDER_FRAGMENT = "f_h_o";
    public static final String MY_BALANCE_FRAGMENT = "m";
    public static final String SEND_QUESTION_FRAGMENT = "s";
    public static final String OLD_TRIPS_FRAGMENT = "p";
    public static final String FULL_IMAGE_FN = "fi_fn";

// ___ firBase titles _____________________________________________________________

    public static final String HAVE_TRIP = "TripOffer";
    public static final String CANCEL_TRIP = "CancelTrip";
    public static final String DEACTIVATE = "DeActivate";


// ___ KEYS _____________________________________________________________


    //من اجل تمرير الموقع من واجهة لواجهة عن طريق ال intent
    public static final String LAT = "lat";
    //من اجل تمرير الموقع من واجهة لواجهة عن طريق ال intent
    public static final String LNG = "lng";


    /**
     * @deprecated بسبب الاعتماد على أثر تم الغاء هذه السيرفس
     */
    //من أجل ريسيفر تغييرات الموقع
    public static final String LOCATION_CHANGE = "l_c";

    //مسافة التحرك
    public static final long DISTANCE_CHANGE = 50L;
    //وقت التحرك
    public static final long TIME_CHANGE = 0L;
    //ترويسة رقم الهاتف
    public static final String _963 = "963";

// ___ others _____________________________________________________________

    /**
     * get rating user from api and set it with rating Bar
     *
     * @param activity  for get context
     * @param id        user id for get rate from api
     * @param ratingBar for init rate with it
     */
    public static void GET_AND_SET_RATING(Activity activity, int id, RatingBar ratingBar) {

        UserViewModel.getInstance().getRate(id);
        UserViewModel.getInstance().getRateLiveData.observe((LifecycleOwner) activity, pair -> {
            if (pair != null) {
                if (pair.first != null)
                    ratingBar.setRating((float) pair.first.result.rate);
                else
                    try {
                        Toast.makeText(activity, pair.second, Toast.LENGTH_SHORT).show();
                    } catch (Exception ignore) {
                    }

            } else
                    TOAST_NO_INTERNET(activity);
        });

    }

    public static String getError(int cod, ResponseBody response) {
        switch (cod) {
            case 401: {
                return " المستخدم الحالي لم يسجل الدخول ";
            }
            case 503: {

                return "حدث تغيير في المخدم رمز الخطأ 503 ";
            }

            case 500:
            default: {
                String e = "";
                try {
                    e = response.string();
                } catch (IOException ignore) {
                }

                try {
                    String message = gson.fromJson(e, BaseResponse.class).error.message;
                    if (message != null)
                        return message;
                    else return e;
                } catch (Exception ignore) {
                    return e;
                }
            }
        }
    }


}

