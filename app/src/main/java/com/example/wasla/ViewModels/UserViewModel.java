package com.example.wasla.ViewModels;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wasla.ApiClint.ApiClint;
import com.example.wasla.Models.Request.FirbaseToken;
import com.example.wasla.Models.Request.RateRequest;
import com.example.wasla.Models.Request.SendMessage;
import com.example.wasla.Models.Request.UpdateUserInfo;
import com.example.wasla.Models.Response.AvailableModel;
import com.example.wasla.Models.Response.RateResponse;
import com.example.wasla.Models.Response.UserInfo;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.wasla.AppConfig.Cods.getError;
import static com.example.wasla.AppConfig.SharedPreference.CASH_SEND_FIR_TOKEN;
import static com.example.wasla.AppConfig.SharedPreference.CASH_USER_INFO;

public class UserViewModel extends ViewModel {
    public static String TAG = "SAED_";
    ApiClint apiClint = new ApiClint();

    public MutableLiveData<Pair<UserInfo, String>> userByIdLiveData;
    public MutableLiveData<Pair<UserInfo, String>> updateUserInfoLiveData;
    public MutableLiveData<Pair<RateResponse, String>> getRateLiveData;
    public MutableLiveData<Pair<Boolean, String>> postRateLiveData;
    public MutableLiveData<Pair<JsonObject, String>> postFirTokenLiveData;
    public MutableLiveData<Pair<AvailableModel, String>> locationLiveData;
    public MutableLiveData<Pair<Boolean, String>> sendMessageLiveData;


    private static UserViewModel VIEW_MODEL;

    public static UserViewModel getInstance() {
        if (VIEW_MODEL != null) {
            return VIEW_MODEL;
        }
        VIEW_MODEL = new UserViewModel();
        return VIEW_MODEL;
    }


    public void getUserInfo(int id) {
        userByIdLiveData = new MutableLiveData<>();
        apiClint.getAPI().getUserById(id).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NotNull Call<UserInfo> call, @NotNull Response<UserInfo> response) {

                if (response.isSuccessful()) {
                    userByIdLiveData.setValue(new Pair<>(response.body(), null));
                    CASH_USER_INFO(response.body());
                } else
                    userByIdLiveData.setValue(new Pair<>(null, getError(response.code(), response.errorBody())));

            }

            @Override
            public void onFailure(@NotNull Call<UserInfo> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: userByIdLiveData ", t);
                userByIdLiveData.setValue(null);
            }
        });
    }
    
    public void updateUserInfo(UpdateUserInfo info) {
        updateUserInfoLiveData = new MutableLiveData<>();

        if (info == null)
            return;

        apiClint.getAPI().updateUserInfo(info).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NotNull Call<UserInfo> call, @NotNull Response<UserInfo> response) {

                if (response.isSuccessful()) {
                    CASH_USER_INFO(response.body());
                    updateUserInfoLiveData.setValue(new Pair<>(response.body(), null));

                } else
                    updateUserInfoLiveData.setValue(new Pair<>(null, getError(response.code(), response.errorBody())));
            }

            @Override
            public void onFailure(@NotNull Call<UserInfo> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: updateUserInfoLiveData ", t);
                updateUserInfoLiveData.setValue(null);
            }
        });
    }

    public void getRate(int userId) {
        getRateLiveData = new MutableLiveData<>();
        apiClint.getAPI().getUserRate(userId).enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(@NotNull Call<RateResponse> call, @NotNull Response<RateResponse> response) {

                if (response.isSuccessful())
                    getRateLiveData.setValue(new Pair<>(response.body(), null));
                else
                    getRateLiveData.setValue(new Pair<>(null, getError(response.code(), response.errorBody())));

            }

            @Override
            public void onFailure(@NotNull Call<RateResponse> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: getRateLiveData ", t);
                getRateLiveData.setValue(null);
            }
        });
    }

    public void postRate(RateRequest rate) {
        postRateLiveData = new MutableLiveData<>();
        apiClint.getAPI().postRate(rate).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    postRateLiveData.setValue(new Pair<>(true, null));
                } else
                    postRateLiveData.setValue(new Pair<>(false, getError(response.code(), response.errorBody())));

            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: postRateLiveData ", t);
                postRateLiveData.setValue(null);
            }
        });
    }

    public void postFirToken(String firToken) {
        postFirTokenLiveData = new MutableLiveData<>();
        apiClint.getAPI().sendTokenFirBase(new FirbaseToken(firToken)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                if (!response.isSuccessful()) {

                    new Handler(Looper.getMainLooper()).postDelayed(() -> {

                        Log.e(TAG, "fireToken not send : " + response.code());
                        call.clone().enqueue(this);

                    }, 10000);
                } else {

                    CASH_SEND_FIR_TOKEN();
                    Log.d(TAG, "fireToken sanded  ");

                }
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                new Handler(Looper.getMainLooper()).postDelayed(() ->
                        call.clone().enqueue(this), 5000);

                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void getMyLocation(boolean newLiveData, int myId) {
        if (newLiveData)
            locationLiveData = new MutableLiveData<>();
        apiClint.getAPI().getMyLocation(myId).enqueue(new Callback<AvailableModel>() {
            @Override
            public void onResponse(@NotNull Call<AvailableModel> call, @NotNull Response<AvailableModel> response) {
                if (response.isSuccessful())
                    locationLiveData.setValue(new Pair<>(response.body(), null));
                else
                    locationLiveData.setValue(new Pair<>(null, getError(response.code(), response.errorBody())));
            }


            @Override
            public void onFailure(@NotNull Call<AvailableModel> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: locationLiveData ", t);
                locationLiveData.setValue(null);
            }
        });
    }

    public void sendMessage(SendMessage message) {
        sendMessageLiveData = new MutableLiveData<>();
        apiClint.getAPI().sendMessage(message).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                if (response.isSuccessful())
                    sendMessageLiveData.setValue(new Pair<>(true, null));
                else
                    sendMessageLiveData.setValue(new Pair<>(false, getError(response.code(), response.errorBody())));
            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: sendMessageLiveData", t);
                sendMessageLiveData.setValue(null);
            }
        });
    }

}
