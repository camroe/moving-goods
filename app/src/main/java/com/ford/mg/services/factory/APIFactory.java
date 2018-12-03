package com.ford.mg.services.factory;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cam.activityswitcher.R;
import com.ford.mg.constant.Constants;
import com.ford.mg.services.IF.OrderCombinationIF;
import com.ford.mg.services.IF.OrderIF;
import com.ford.mg.services.impl.LocalOrderAPI;
import com.ford.mg.services.impl.LocalOrderCombinationAPI;
import com.ford.mg.services.impl.RemoteCombinationAPI;
import com.ford.mg.services.impl.RemoteOrderAPI;

public class APIFactory {
    public static final String TAG = APIFactory.class.getCanonicalName();

    static boolean localFlag;
    static String server;
    static int port;

    public static OrderIF getOrderAPI(Activity callingActivity) {
        String methodTag = TAG + ".getOrderAPI";
        OrderIF returnOrderAPI;
        setPreferences(callingActivity);
        if (localFlag) {
            returnOrderAPI = new LocalOrderAPI();
            Log.d(methodTag, "LOCAL API CREATED");
        } else {
            returnOrderAPI = new RemoteOrderAPI(server, port);
            Log.d(methodTag, "REMOTE API CREATED");
        }
        return returnOrderAPI;
    }

    public static OrderCombinationIF getOrderCombinationAPI(Activity callingActivity) {
        String methodTag = TAG + ".getOrderCombinationAPI";
        OrderCombinationIF returnOrderAPI;
        setPreferences(callingActivity);
        if (localFlag) {
            returnOrderAPI = new LocalOrderCombinationAPI();
            Log.d(methodTag, "LOCAL API CREATED");
        } else {
            returnOrderAPI = new RemoteCombinationAPI(server, port);
            Log.d(methodTag, "REMOTE API CREATED");
        }
        return returnOrderAPI;
    }

    private static void setPreferences(Activity callingActivity) {
        String methodTag = TAG + ".setPreferences";
        SharedPreferences preferences = callingActivity.getPreferences(Context.MODE_PRIVATE);
        Log.d(methodTag, preferences
                .getString(callingActivity.getString(R.string.localremoteKey), "true"));
        //Get the system settings
        localFlag = preferences.getBoolean(callingActivity.getString(R.string.localremoteKey), true);
        server = preferences.getString(callingActivity.getString(R.string.serverKey), Constants.SERVER_IP);
        port = preferences.getInt(callingActivity.getString(R.string.portKey), Constants.SERVER_PORT);

    }
}
