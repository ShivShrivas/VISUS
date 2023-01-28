package com.org.visus.utility;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;

import com.org.visus.R;


public class ConnectionUtility {

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        return isConnected;
    }


    public static void AlertDialogForNoConnectionAvaialble(final Context context) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_NoTitleBar_Fullscreen);
        dialog.setContentView(
                R.layout.custom_dialog_check_internet);
        dialog.show();
        Button btn_enable_connection = dialog.findViewById(R.id.btn_enable_connection);
        btn_enable_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);
                dialog.show();*/
                context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS), null);

            }
        });
    }


}
