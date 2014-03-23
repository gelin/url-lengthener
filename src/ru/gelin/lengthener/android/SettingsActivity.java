package ru.gelin.lengthener.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 *  Activity with settings.
 */
public class SettingsActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

}