package com.indushospitals.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by think360 on 01/02/18.
 */

public class ConnectivityReceiver {

    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) AppController.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }
}