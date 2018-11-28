package com.ford.mg.fragments;


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

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, String s) {
//        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        System.out.println("ON CREATE");
        showPreferences();
    }
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.settings);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "CALLED onOptionsItemSelected" + item);
        return super.onOptionsItemSelected(item);
    }

    private void showPreferences() {
        SwitchPreferenceCompat switchPreference = (SwitchPreferenceCompat) findPreference("localremoteKey");
        EditTextPreference server = (EditTextPreference) findPreference("serverKey");
        EditTextPreference port = (EditTextPreference) findPreference("portKey");
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

    @Override
    public void onStart() {
        System.out.println("ON_START:PREFERENCES (MAIN WINDOW):");
        showPreferences();
        System.out.flush();
        super.onStart();
    }

    @Override
    public void onResume() {
        System.out.println("ON_RESUME:PREFERENCES (MAIN WINDOW):");
        showPreferences();
        System.out.flush();
        super.onResume();
    }

}
