package com.org.visus.apis;

import static com.org.visus.apis.APIUrl.BASE_URL;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(final Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (retrofit == null) {
            OkHttpClient.Builder httpClient;
            httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(20, TimeUnit.MINUTES); // connect timeout
            httpClient.readTimeout(20, TimeUnit.MINUTES);
            httpClient.addInterceptor(loggingInterceptor);
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        }
        return retrofit;
    }
}