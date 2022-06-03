package com.example.wasla.ApiClint;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.wasla.AppConfig.SharedPreference.GET_ACCESS_TOKEN;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer "+ GET_ACCESS_TOKEN())
                .build();

        return chain.proceed(newRequest);
    }
}
