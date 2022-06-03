package com.example.wasla.ApiClint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MapApiClient {

    private static final String BASE_URL = "https://maps.googleapis.com/";
    private static Retrofit retrofit;
    private static API apIs;


    private Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            return retrofit;
        }
        return retrofit;
    }


    public API getApIs() {
        if (apIs == null) {
            apIs = getRetrofitInstance().create(API.class);
        }
        return apIs;
    }

}
