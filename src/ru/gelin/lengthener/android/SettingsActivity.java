package ru.gelin.lengthener.android;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 *  Activity with settings.
 */
public class SettingsActivity extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: tablet support
        addPreferencesFromResource(R.xml.preferences);
    }

}