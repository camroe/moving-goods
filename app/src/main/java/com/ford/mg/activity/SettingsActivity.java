package com.ford.mg.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.ford.mg.activity.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private String TAG = SettingsActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "CALLED onOptionsItemSelected" + item);
        Toast.makeText(this, "Settings Fragment SELECTED",
                Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }


    private void showPreferences() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String port = sharedPreferences.getString("portKey", null);
        Boolean switchPreference = sharedPreferences.getBoolean("localremoteKey", false);
        String server = sharedPreferences.getString("serverKey", null);

        System.out.println();
        if (null != switchPreference)
            System.out.println(":ISCHECKED: " + switchPreference);
        else
            System.out.println("SwitchPreference is NULL");
        if (null != server)
            System.out.println("SERVER:" + server);
        else
            System.out.println("SERVER is NULL");
        if (null != port)
            System.out.println("PORT:" + port);
        else
            System.out.println("PORT is NULL");
        System.out.println();

    }

}
