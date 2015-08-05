package ru.gelin.lengthener.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 *  Activity to setup strings where to remove query.
 */
public class RemoveQueryDomainsSettingsActivity extends ActionBarActivity {

    StringsListFragmentBase fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, getFragment())
                .commit();
    }

    Fragment getFragment() {
        if (this.fragment == null) {
            this.fragment = StringsListFragmentBase.newInstance(
                    AndroidLengthenerSettings.REMOVE_QUERY_DOMAINS_PREFIX, R.string.add_domain);
        }
        return this.fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}