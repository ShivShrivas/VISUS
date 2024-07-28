package com.org.visus.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.models.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrefUtils {

    public static final String DEVICEServerID = "DEVICEServerID";
    public static final String Token = "Token";
    public static final String IsDeviceVerified = "IsDeviceVerified";
    public static final String PINCODE = "PINCODE";
    public static final String INV_code = "INV_code";
    public static final String INV_name = "INV_name";
    public static final String InvestigatorID = "InvestigatorID";
    public static final String ContactNumber = "ContactNumber";
    public static final String ContactNumber2 = "ContactNumber2";
    public static final String Email = "Email";
    public static final String PanNumber = "PanNumber";
    public static final String JoiningDate = "JoiningDate";
    public static final String Status = "Status";

    private static String token = "";

    private static final SharedPreferences preferences = null;

    public static String saveToPrefs(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
        return key;
    }

    public static String getFromPrefs(Context context, String key) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        try {
            return sharedPrefs.getString(key, "");

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void removeFromSharedPreferences(Context context, String key) {
        if (context != null) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPrefs != null) sharedPrefs.edit().remove(key).commit();
        }
    }

    public static String getToken(Context context) {
        ApiService apiService;
        apiService = ApiClient.getClient(context).create(ApiService.class);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        //String JSONObject = gson.toJson(getHardwareAndSoftwareInfoList);
        //Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        //String prettyJsonForLogin = prettyGson.toJson(JSONObject);
        Call<TokenResponse> call2 = apiService.getToken("admin", "Visus#2022@Api2021", "password");
        call2.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.body() != null) {
                    final TokenResponse tokenReponse = response.body();
                    if (tokenReponse != null) {
                        PrefUtils.saveToPrefs(context, PrefUtils.Token, tokenReponse.getAccessToken() != null ? tokenReponse.getAccessToken() : "");
                        token = PrefUtils.getFromPrefs(context, PrefUtils.Token);
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(context, "fail " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return token;
    }
}
