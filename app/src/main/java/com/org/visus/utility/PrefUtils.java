package com.org.visus.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {

    public static final String DEVICEServerID = "DEVICEServerID";
    public static final String Token = "Token";
    public static final String IsDeviceVerified = "IsDeviceVerified";
    public static final String PINCODE = "PINCODE";
    public static final String INV_code = "INV_code";
    public static final String INV_name = "INV_name";
    public static final String InvestigatorID = "InvestigatorID";


    private static final SharedPreferences preferences = null;

    public static String saveToPrefs(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
        return key;
    }

    public static String getFromPrefs(Context context, String key) {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        try {
            return sharedPrefs.getString(key, "");

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void removeFromSharedPreferences(Context context, String key) {
        if (context != null) {
            SharedPreferences sharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            if (sharedPrefs != null)
                sharedPrefs.edit().remove(key).commit();
        }
    }
}
