package com.example.wasla.ApiClint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClint {

//     public static final String BASE_URL = "http://flcosama-001-site1.ctempurl.com/";
    public static final String BASE_URL = "http://185.84.236.62/";

    public static Retrofit retrofit;

    private static API api;

    public static TokenInterceptor interceptor = new TokenInterceptor();

    public static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build();

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
//                    .client(getUnsafeOkHttpClient().build())
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            return retrofit;
        }
        return retrofit;
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            TrustManager[] arrayOfTrustManager = new TrustManager[1];
            X509TrustManager x509TrustManager = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            arrayOfTrustManager[0] = x509TrustManager;
            SSLContext sSLContext = SSLContext.getInstance("SSL");
            SecureRandom secureRandom = new SecureRandom();

            sSLContext.init(null, arrayOfTrustManager, secureRandom);
            SSLSocketFactory sSLSocketFactory = sSLContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.sslSocketFactory(sSLSocketFactory, (X509TrustManager) arrayOfTrustManager[0]);
            HostnameVerifier hostnameVerifier = (param1String, param1SSLSession) -> true;
            builder.hostnameVerifier(hostnameVerifier);
            builder.addInterceptor(interceptor);

            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);

            return builder;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }


    public API getAPI() {

        if (api != null)
            return api;

        api = getRetrofitInstance().create(API.class);
        return api;


    }

}
