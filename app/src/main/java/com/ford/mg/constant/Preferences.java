package com.ford.mg.constant;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.cam.activityswitcher.R;

public class Preferences {
    private static String TAG = Preferences.class.getCanonicalName();
    private static Preferences instance = null;
    private static Context context;
    private String server;
    private boolean localRemote;
    private int port;

    private SharedPreferences sharedPreferences;

    public static Preferences getInstance(Activity activity) {
        if (instance == null)
            instance = new Preferences();
        return update(instance, activity);
    }

    private static Preferences update(Preferences instance, Activity activity) {
        instance.setSharedPreferences(activity.getPreferences(Context.MODE_PRIVATE));
        instance.setSharedPreferences(activity.getSharedPreferences("settings.xml",Context.MODE_PRIVATE));
        setLocalValues(instance, activity);
        Log.d(TAG,instance.toString());
        return instance;
        /*
         preferences = this.getPreferences(Context.MODE_PRIVATE);
        setContentView(R.layout.activity_customerorder);
        Log.d(methodTag, preferences
                .getString(this.getString(R.string.localremoteKey), "true"));
        boolean localFlag = preferences.getBoolean(this.getString(R.string
        .localremoteKey), true);

         */


    }

    private static void setLocalValues(Preferences instance, Activity activity) {
        String methodTAG = TAG + ":setLocalValues";
        Log.d(methodTAG, "CONTAINS LOCAL/REMOTE KEY:" +String.valueOf(instance.getSharedPreferences().contains(activity
                .getString(R.string.localremoteKey))));
        Log.d(methodTAG, "CONTAINS SERVER:" +String.valueOf(instance.getSharedPreferences().contains(activity
                .getString(R.string.serverKey))));
        Log.d(methodTAG, "CONTAINS PORT:" +String.valueOf(instance.getSharedPreferences().contains(activity
        .getString(R.string.portKey))));
        instance.setLocalRemote(instance
                .getSharedPreferences()
                .getBoolean(activity
                        .getString(R.string.localremoteKey), true));

        instance.setPort(instance
        .getSharedPreferences()
        .getInt(activity
        .getString(R.string.portKey), Integer.parseInt(activity
        .getString(R.string.default_port_config))));

        instance.setServer(instance
        .getSharedPreferences()
        .getString(activity
        .getString(R.string.serverKey),activity
        .getString(R.string.default_server_config)));
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isLocalRemote() {
        return localRemote;
    }

    public void setLocalRemote(boolean localRemote) {
        this.localRemote = localRemote;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("\n")
                .append("Local/Remote: " + this.localRemote)
                .append("\n")
                .append("Server:" + this.getServer())
                .append("\n")
                .append("Port:"+this.getPort())
                .append("\n")
        .trimToSize();
        return sb.toString();
    }
}
