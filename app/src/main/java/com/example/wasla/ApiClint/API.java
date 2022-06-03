package com.example.wasla.ApiClint;

import com.example.wasla.Models.MapModel.RoutePointsResult;
import com.example.wasla.Models.Request.ChangePassRequest;
import com.example.wasla.Models.Request.FirbaseToken;
import com.example.wasla.Models.Request.LoginRequest;
import com.example.wasla.Models.Request.RateRequest;
import com.example.wasla.Models.Request.SendLocation;
import com.example.wasla.Models.Request.SendMessage;
import com.example.wasla.Models.Request.UpdateUserInfo;
import com.example.wasla.Models.Response.AvailableModel;
import com.example.wasla.Models.Response.BalanceOfToday;
import com.example.wasla.Models.Response.LoginResponse;
import com.example.wasla.Models.Response.MyBalance;
import com.example.wasla.Models.Response.RateResponse;
import com.example.wasla.Models.Response.SingleTrip;
import com.example.wasla.Models.Response.Trips;
import com.example.wasla.Models.Response.UserInfo;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @FormUrlEncoded
    @POST("api/TokenAuth/Authenticate")
    Call<LoginResponse> confirmCod(@Field("") String cod);

    @GET("api/services/app/UserService/GetUserById")
    Call<UserInfo> getUserById(@Query("id") int id);


    @PUT("api/services/app/UserService/UpdateInformation")
    Call<UserInfo> updateUserInfo(@Body UpdateUserInfo info);

    @GET("api/services/app/UserInformation/DownloadImage/{id}")
    Call<ResponseBody> downloadImage(@Path("id") int idUser);
//                            try {
//        Base64.encode(response.body().bytes(),Base64.DEFAULT);
//    } catch (
//    IOException e) {
//        e.printStackTrace();
//    }

    @GET("api/services/app/UserService/GetUserRate")
    Call<RateResponse> getUserRate(@Query("id") int userId);

    @POST("api/services/app/UserService/PostRate")
    Call<JsonObject> postRate(@Body RateRequest rate);

    @GET("api/services/app/UserService/MakeDriverAavailable")
    Call<AvailableModel> makeDriverAvailable();

    @GET("api/services/app/Order/Get")
    Call<SingleTrip> getTripById(@Query("Id") int tripId);

    @FormUrlEncoded
    @POST("api/services/app/Order/AcceptTrip")
    Call<JsonObject> acceptTrip(@Field("id") int tripId);

    @FormUrlEncoded
    @POST("api/services/app/Order/StartTrip")
    Call<JsonObject> startTrip(@Field("id") int tripId);

    @FormUrlEncoded
    @POST("api/services/app/Order/EndTrip")
    Call<JsonObject> endTrip(@Field("id") int tripId);

    @GET("api/services/app/UserService/MakeDriverUnAavailable")
    Call<JsonObject> makeDriverUnAvailable();

    @GET("api/services/app/Order/GetAavailableTrips")
    Call<Trips> getAvailableTrips();

    @POST("api/TokenAuth/Authenticate")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("api/services/app/Baclance/getMyBalanceOfToday")
    Call<BalanceOfToday> getDayInfo();

    @GET("api/services/app/Baclance/getMyBalance")
    Call<MyBalance> getMyBalance();

    @GET("api/services/app/Order/GetDriverTrips")
    Call<Trips> getOldTrips();

    @GET("/api/services/app/Order/GetDriverScheduledTrips")
    Call<Trips> getScheduledTrips();

    /**
     * Map Request
     */

    @GET("maps/api/directions/json")
    Call<RoutePointsResult> getRoutePoints(@Query("mode") String mode,
                                           @Query("transit_routing_preference") String preference,
                                           @Query("origin") String origin,
                                           @Query("destination") String destination,
                                           @Query("key") String key);


    @POST("api/services/app/UserService/InsertFireBaseTokenForUser")
    Call<JsonObject> sendTokenFirBase(@Body FirbaseToken token);


    @POST("api/services/app/UserService/sendlocationToClient")
    Call<JsonObject> sendLocation(@Body SendLocation sendLocation);

    @POST("api/services/app/Account/ForgetPassword")
    Call<JsonObject> forgetPassword(@Body JsonObject json);

    @POST("api/services/app/Account/ResetNewPassword")
    Call<JsonObject> changePassword(@Body ChangePassRequest request);

    @POST("api/services/app/UserService/sendSignalToClientDriverIsNear")
    Call<JsonObject> sendSignalToClient(@Body SendLocation sendLocation);

    @FormUrlEncoded
    @POST("api/services/app/Account/ResendCode")
    Call<JsonObject> resendCod(@Field("input") String phoneNumber);

    @GET("api/services/app/UserService/GetDriversLocation")
    Call<AvailableModel> getMyLocation(@Query("driverid") int myId);

    @POST("api/services/app/Messages/CreateMessageForUser")
    Call<JsonObject> sendMessage(@Body SendMessage sendMessage);
}
