package ru.gelin.lengthener.android;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LengthenerApplication extends Application {

    public static final String FIRST_RUN_PREFERENCE = "first_run";

    @Override
    public void onCreate() {
        super.onCreate();
        //http://stackoverflow.com/questions/2227604/is-there-on-install-event-in-android
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean firstRun = prefs.getBoolean(FIRST_RUN_PREFERENCE, true);
//        Log.d(Tag.TAG, "first run = " + firstRun);
        prefs.edit().putBoolean(FIRST_RUN_PREFERENCE, false).commit();

        StringsListAdapter adapter = new StringsListAdapter(getApplicationContext(),
                AndroidLengthenerSettings.REMOVE_PARAMS_PATTERNS_PREFIX);
        if (firstRun && adapter.getCount() == 0) {
            adapter.addString("utm_*");     // put this pattern for the first run
        }
    }
}
