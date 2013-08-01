package ru.gelin.lengthener.android;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 *  Activity to setup domains where to remove query.
 */
public class RemoveQueryDomainsSettingsActivity extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: pass params to fragment
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new DomainsListFragment())
                .commit();
    }

}