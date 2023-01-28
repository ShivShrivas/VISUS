package com.org.visus.apis;

import static com.org.visus.apis.APIUrl.BASE_URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(final Context context) {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient;
            httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(2, TimeUnit.MINUTES); // connect timeout
            httpClient.readTimeout(2, TimeUnit.MINUTES);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    private static Cache provideCache(Context context) {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(), "http-cache"),
                    30 * 1024 * 1024); // 30 MB
        } catch (Exception e) {
            Log.e("@@@@", "Could not create Cache!");
        }
        return cache;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static Interceptor provideOfflineCacheInterceptor(final Context context) {
        return new Interceptor() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isOnline(context)) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }
}