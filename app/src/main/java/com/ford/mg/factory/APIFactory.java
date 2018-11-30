package com.ford.mg.factory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cam.activityswitcher.R;
import com.ford.mg.cloud.IF.OrderIF;
import com.ford.mg.cloud.impl.LocalOrderAPI;
import com.ford.mg.cloud.impl.RemoteOrderAPI;

public class APIFactory {
    public static final String TAG = APIFactory.class.getCanonicalName();

    public static OrderIF getOrderAPI(Activity callingActivity) {
        String methodTag = TAG + ".getOrderAPI";
        OrderIF returnOrderAPI;
        SharedPreferences preferences = callingActivity.getPreferences(Context.MODE_PRIVATE);
        Log.d(methodTag, preferences
                .getString(callingActivity.getString(R.string.localremoteKey), "true"));
        boolean localFlag = preferences.getBoolean(callingActivity.getString(R.string.localremoteKey), true);
        Log.d(methodTag, String.valueOf(localFlag));
        Log.d(methodTag, "Value of boolean localflag is: " + localFlag);
        if (localFlag) {
            returnOrderAPI = new LocalOrderAPI();
            Log.d(methodTag, "LOCAL API CREATED");
        } else {
            returnOrderAPI = new RemoteOrderAPI();
            Log.d(methodTag, "REMOTE API CREATED");
        }
        return returnOrderAPI;
    }
}
