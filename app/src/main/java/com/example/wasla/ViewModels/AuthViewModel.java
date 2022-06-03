package com.example.wasla.ViewModels;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wasla.ApiClint.ApiClint;
import com.example.wasla.Models.BaseResponse;
import com.example.wasla.Models.Request.ChangePassRequest;
import com.example.wasla.Models.Request.LoginRequest;
import com.example.wasla.Models.Response.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {


    public static String TAG = "SAED_AUTH";
    ApiClint apiClint = new ApiClint();

    Gson gson = new Gson();

    //    public MutableLiveData<Pair<ConfirmCodResponse, String>> confirmCodLiveData;
    public MutableLiveData<Pair<LoginResponse, String>> loginLiveData;
    public MutableLiveData<Pair<Boolean, String>> forgetPassLiveData;
    public MutableLiveData<Pair<Boolean, String>> changePassLiveData;
    public MutableLiveData<Pair<Boolean, String>> resendLiveData;


    private static AuthViewModel VIEW_MODEL;

    public static AuthViewModel getInstance() {
        if (VIEW_MODEL != null) {
            return VIEW_MODEL;
        }
        VIEW_MODEL = new AuthViewModel();
        return VIEW_MODEL;
    }

    public void login(LoginRequest login) {

        this.loginLiveData = new MutableLiveData<>();

        apiClint.getAPI().login(login).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    loginLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        loginLiveData.setValue(new Pair<>(null, messageError));
                    } catch (Exception ignored) {
                        loginLiveData.setValue(new Pair<>(null, "catch"));
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                loginLiveData.setValue(null);
            }
        });
    }

    public void forgetPassWord(String phone) {
        forgetPassLiveData = new MutableLiveData<>();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phoneNumber", phone);
        apiClint.getAPI().forgetPassword(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    forgetPassLiveData.setValue(new Pair<>(true, null));
                } else {
                    try {
                        String error = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        forgetPassLiveData.setValue(new Pair<>(false, error));
                    } catch (Exception ignore) {
                        forgetPassLiveData.setValue(new Pair<>(false, "حدث خطأ غير متوقع "));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                forgetPassLiveData.setValue(null);
            }
        });
    }


    public void changePassword(ChangePassRequest changePassword) {
        changePassLiveData = new MutableLiveData<>();

        apiClint.getAPI().changePassword(changePassword).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    changePassLiveData.setValue(new Pair<>(true, null));
                } else {
                    try {
                        String error = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        changePassLiveData.setValue(new Pair<>(false, error));
                    } catch (Exception ignore) {
                        changePassLiveData.setValue(new Pair<>(false, "حدث خطأ غير متوقع "));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                changePassLiveData.setValue(null);
            }
        });
    }

    public void resendCod(String phone) {
        resendLiveData = new MutableLiveData<>();
        try {

            apiClint.getAPI().resendCod(phone).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.code() == 200) {
                        resendLiveData.setValue(new Pair<>(true, null));
                    } else {
                        String error = null;
                        try {
                            error = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                            resendLiveData.setValue(new Pair<>(false, error));

                        } catch (Exception ignored) {
                            resendLiveData.setValue(new Pair<>(false, "catch"));
                        }

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    resendLiveData.setValue(null);
                }
            });


        } catch (Exception ignore) {
            resendLiveData.setValue(null);
        }
    }
//    public void ConfirmCod(String cod) {
//        confirmCodLiveData = new MutableLiveData<>();
//        apiClint.getAPI().confirmCod(cod).enqueue(new Callback<ConfirmCodResponse>() {
//            @Override
//            public void onResponse(Call<ConfirmCodResponse> call, Response<ConfirmCodResponse> response) {
//                Log.d(TAG, "onResponse:" + response.body());
//                Log.d(TAG, "onResponse:" + response.errorBody());
//
//                if (response.isSuccessful()) {
//                    confirmCodLiveData.setValue(new Pair<>(response.body(), null));
//                } else {
//                    try {
//                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
//                        confirmCodLiveData.setValue(new Pair<>(null, messageError));
//                    } catch (IOException ignored) {
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ConfirmCodResponse> call, Throwable throwable) {
//                Log.e(TAG_ERROR, "onFailure: " + throwable.getMessage());
//                confirmCodLiveData.setValue(null);
//            }
//        });
//    }
}
