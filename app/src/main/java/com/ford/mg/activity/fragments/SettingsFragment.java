package com.ford.mg.activity.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;
import android.view.MenuItem;

import com.example.cam.activityswitcher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private String TAG = SettingsFragment.class.getCanonicalName();

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, String s) {
//        super.onCreate(savedInstanceState);
        setPreferencesFromResource(R.xml.settings,s);
        System.out.println("ON CREATE Preferences");
        showPreferences();    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "CALLED onOptionsItemSelected" + item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        showPreferences();
    }

    private void showPreferences() {
        SwitchPreferenceCompat switchPreference = (SwitchPreferenceCompat) findPreference(this.getString(R.string.localremoteKey));
        EditTextPreference server = (EditTextPreference) findPreference(getString(R.string.serverKey));
        EditTextPreference port = (EditTextPreference) findPreference(getString(R.string.portKey));
        System.out.println();
        if (null != switchPreference)
            System.out.println(":ISCHECKED: " + switchPreference.isChecked());
        else
            System.out.println("SwitchPreference is NULL");
        if (null != server)
            System.out.println("SERVER:" + server.getText());
        else
            System.out.println("SERVER is NULL");
        if (null != port)
            System.out.println("PORT:" + port.getText());
        else
            System.out.println("PORT is NULL");
        System.out.println();
    }
}
