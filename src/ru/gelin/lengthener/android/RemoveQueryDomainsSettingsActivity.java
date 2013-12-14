package ru.gelin.lengthener.android;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 *  Activity to setup domains where to remove query.
 */
public class RemoveQueryDomainsSettingsActivity extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,
                        DomainsListFragment.newInstance(AndroidLengthenerSettings.REMOVE_QUERY_DOMAINS_PREFIX))
                .commit();
    }

}