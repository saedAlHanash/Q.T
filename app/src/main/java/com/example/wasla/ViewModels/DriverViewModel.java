package com.example.wasla.ViewModels;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wasla.ApiClint.ApiClint;
import com.example.wasla.Models.BaseResponse;
import com.example.wasla.Models.Request.SendLocation;
import com.example.wasla.Models.Response.AvailableModel;
import com.example.wasla.Models.Response.BalanceOfToday;
import com.example.wasla.Models.Response.MyBalance;
import com.example.wasla.Models.Response.SingleTrip;
import com.example.wasla.Models.Response.Trips;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverViewModel extends ViewModel {
    public static String TAG = "SAED_DRIVER";
    ApiClint apiClint = new ApiClint();


    Gson gson = new Gson();

    public MutableLiveData<Pair<AvailableModel, String>> availableDriverLiveData;
    // public   MutableLiveData<Pair<JsonObject, String>> unAvailableDriverLiveData;
    public MutableLiveData<Pair<SingleTrip, String>> tripByIdLiveData;
    public MutableLiveData<Pair<Boolean, String>> acceptTripLiveData;
    public MutableLiveData<Pair<Boolean, String>> startTripLiveData;
    public MutableLiveData<Pair<Boolean, String>> endTripLiveData;
    public MutableLiveData<Pair<Trips, String>> availableTripsLiveData;
    public MutableLiveData<Pair<Trips, String>> oldTripsLiveData;
    public MutableLiveData<Pair<BalanceOfToday, String>> dayInfoLiveData;
    public MutableLiveData<Pair<MyBalance, String>> myBalanceLiveData;
    public MutableLiveData<Pair<Boolean, String>> sendSignalLiveData;
    public MutableLiveData<Pair<Trips, String>> scheduledTripsLiveData;

    private static DriverViewModel VIEW_MODEL;

    public static DriverViewModel getInstance() {
        if (VIEW_MODEL != null) {
            return VIEW_MODEL;
        }
        VIEW_MODEL = new DriverViewModel();
        return VIEW_MODEL;
    }

    public void makeDriverAvailable() {

        availableDriverLiveData = new MutableLiveData<>();
        apiClint.getAPI().makeDriverAvailable().enqueue(new Callback<AvailableModel>() {
            @Override
            public void onResponse(Call<AvailableModel> call, Response<AvailableModel> response) {

                if (response.isSuccessful()) {
                    availableDriverLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        availableDriverLiveData.setValue(new Pair<>(null, messageError));
                    } catch (Exception e) {
                        availableDriverLiveData.setValue(new Pair<>(null, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));
                        Log.e(TAG, "catch error: " + e);
                    }
                }
            }

            @Override
            public void onFailure(Call<AvailableModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                availableDriverLiveData.setValue(null);
                new Handler().postDelayed(() -> {
                    call.clone().enqueue(this);
                }, 5000);
            }
        });
    }

    public void makeDriverUnAvailable() {

        apiClint.getAPI().makeDriverUnAvailable().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful())
                    Log.e(TAG, "DriverUnAvailable: ");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                new Handler().postDelayed(() -> {
                    call.clone().enqueue(this);
                }, 5000);
            }
        });

    }

    public void getTripById(int tripId) {

        tripByIdLiveData = new MutableLiveData<>();
        apiClint.getAPI().getTripById(tripId).enqueue(new Callback<SingleTrip>() {
            @Override
            public void onResponse(Call<SingleTrip> call, Response<SingleTrip> response) {
                if (response.isSuccessful()) {
                    tripByIdLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        tripByIdLiveData.setValue(new Pair<>(null, messageError));
                    } catch (Exception ignored) {
                        tripByIdLiveData.setValue(new Pair<>(null, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));
                        Log.e(TAG, "catch error: " + ignored);
                    }
                }
            }

            @Override
            public void onFailure(Call<SingleTrip> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                new Handler().postDelayed(() -> {
                    call.clone().enqueue(this);
                }, 5000);
            }
        });
    }


    public void acceptTrip(int tripId) {
        acceptTripLiveData = new MutableLiveData<>();
        apiClint.getAPI().acceptTrip(tripId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    acceptTripLiveData.setValue(new Pair<>(true, null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        acceptTripLiveData.setValue(new Pair<>(false, messageError));
                    } catch (Exception ignored) {
                        acceptTripLiveData.setValue(new Pair<>(false, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));
                        Log.e(TAG, "catch error: " + ignored);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                acceptTripLiveData.setValue(null);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void startTrip(int tripId) {
        startTripLiveData = new MutableLiveData<>();
        apiClint.getAPI().startTrip(tripId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    startTripLiveData.setValue(new Pair<>(true, null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        startTripLiveData.setValue(new Pair<>(false, messageError));
                    } catch (Exception ignored) {
                        Log.e(TAG, "catch error: " + ignored);
                        startTripLiveData.setValue(new Pair<>(false, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                startTripLiveData.setValue(null);
            }
        });
    }

    public void endTrip(int tripId) {
        endTripLiveData = new MutableLiveData<>();
        apiClint.getAPI().endTrip(tripId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    endTripLiveData.setValue(new Pair<>(true, null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        endTripLiveData.setValue(new Pair<>(null, messageError));
                    } catch (Exception ignored) {
                        endTripLiveData.setValue(new Pair<>(false, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));
                        Log.e(TAG, "catch error: " + ignored);

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                endTripLiveData.setValue(null);
            }
        });
    }

    public void getAvailableTrips() {
        availableTripsLiveData = new MutableLiveData<>();
        apiClint.getAPI().getAvailableTrips().enqueue(new Callback<Trips>() {
            @Override
            public void onResponse(Call<Trips> call, Response<Trips> response) {

                if (response.isSuccessful()) {
                    availableTripsLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        availableTripsLiveData.setValue(new Pair<>(null, messageError));
                    } catch (Exception ignored) {
                        availableTripsLiveData.setValue(new Pair<>(null, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));
                        Log.e(TAG, "catch error: " + ignored);

                    }
                }
            }

            @Override
            public void onFailure(Call<Trips> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                availableTripsLiveData.setValue(null);
            }
        });
    }

    public void getOldTrips(boolean newLiveData) {
        if (newLiveData)
            oldTripsLiveData = new MutableLiveData<>();
        apiClint.getAPI().getOldTrips().enqueue(new Callback<Trips>() {
            @Override
            public void onResponse(Call<Trips> call, Response<Trips> response) {

                if (response.isSuccessful()) {
                    oldTripsLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        oldTripsLiveData.setValue(new Pair<>(null, messageError));
                    } catch (Exception ignored) {
                        oldTripsLiveData.setValue(new Pair<>(null, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));
                        Log.e(TAG, "catch error: " + ignored);

                    }
                }
            }

            @Override
            public void onFailure(Call<Trips> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                oldTripsLiveData.setValue(null);
            }
        });
    }

    public void getDayInfo(boolean newObserver) {
        if (newObserver)
            dayInfoLiveData = new MutableLiveData<>();

        apiClint.getAPI().getDayInfo().enqueue(new Callback<BalanceOfToday>() {
            @Override
            public void onResponse(Call<BalanceOfToday> call, Response<BalanceOfToday> response) {

                Log.d(TAG, "onResponse: " + response.code());

                if (response.isSuccessful()) {
                    dayInfoLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        dayInfoLiveData.setValue(new Pair<>(null, messageError));
                    } catch (Exception ignored) {
                        dayInfoLiveData.setValue(new Pair<>(null, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));
                        Log.e(TAG, "catch error: " + ignored);

                    }
                }
            }

            @Override
            public void onFailure(Call<BalanceOfToday> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                dayInfoLiveData.setValue(null);
            }
        });
    }

    public void getMyBalance() {
        myBalanceLiveData = new MutableLiveData<>();
        apiClint.getAPI().getMyBalance().enqueue(new Callback<MyBalance>() {
            @Override
            public void onResponse(Call<MyBalance> call, Response<MyBalance> response) {

                if (response.isSuccessful()) {
                    myBalanceLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        myBalanceLiveData.setValue(new Pair<>(null, messageError));
                    } catch (Exception ignored) {
                        myBalanceLiveData.setValue(new Pair<>(null, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));
                        Log.e(TAG, "catch error: " + ignored);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyBalance> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                myBalanceLiveData.setValue(null);
            }
        });
    }

    //    public void sendLocationToClient(SendLocation location) {
//        try {
//            apiClint.getAPI().sendLocation(location).execute();
//        } catch (Exception ignore) {
//        }
//    }
    public void sendLocationToClient(SendLocation location) {
        apiClint.getAPI().sendLocation(location).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "onRespondfsgsdfhsfhsfghse: " + response.code());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    public void sendSignal(SendLocation sendLocation) {
        sendSignalLiveData = new MutableLiveData<>();
        apiClint.getAPI().sendSignalToClient(sendLocation).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(TAG, "send signale done *******************************: ");
                if (response.code() == 200) {
                    sendSignalLiveData.setValue(new Pair<>(true, null));
                } else {
                    try {
                        String error = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        sendSignalLiveData.setValue(new Pair<>(false, error));
                    } catch (Exception ignore) {
                        sendSignalLiveData.setValue(new Pair<>(false, "حدث خطأ غير متوقع "));
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                sendSignalLiveData.setValue(null);
            }
        });
    }

    public void getScheduledTrips(boolean newLiveData) {
        if (newLiveData)
            scheduledTripsLiveData = new MutableLiveData<>();

        apiClint.getAPI().getScheduledTrips().enqueue(new Callback<Trips>() {
            @Override
            public void onResponse(Call<Trips> call, Response<Trips> response) {
                if (response.isSuccessful()) {
                    scheduledTripsLiveData.setValue(new Pair<>(response.body(), null));
                } else {
                    try {
                        String messageError = gson.fromJson(response.errorBody().string(), BaseResponse.class).error.message;
                        scheduledTripsLiveData.setValue(new Pair<>(null, messageError));
                    } catch (Exception ignored) {
                        scheduledTripsLiveData.setValue(new Pair<>(null, "حدث خطأ مفاجئ أثناء تنفيذ طلبك "));

                    }
                }
            }

            @Override
            public void onFailure(Call<Trips> call, Throwable t) {
                scheduledTripsLiveData.setValue(null);
            }
        });
    }


}
