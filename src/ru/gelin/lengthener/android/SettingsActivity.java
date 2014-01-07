package ru.gelin.lengthener.android;

import android.app.Activity;
import android.os.Bundle;

/**
 *  Activity with settings.
 */
public class SettingsActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: tablet support
//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content,
//                        SettingsFragment.newInstance())
//                .commit();
        setContentView(R.layout.settings_activity);
    }

}