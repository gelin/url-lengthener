package ru.gelin.lengthener.android;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *  A PreferenceFragment which allows to add and remove domains (as strings) to/from the list.
 */
public class DomainsListFragment extends ListFragment {

    /** Preferences prefix parameter key */
    public static final String PREFS_PREFIX = "prefsPrefix";
    /** Default preferences prefix */
    public static final String DEFAULT_PREFS_PREFIX = "domains_";

    String prefsPrefix = DEFAULT_PREFS_PREFIX;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.prefsPrefix = getArguments().getString(PREFS_PREFIX);
        if (this.prefsPrefix == null) {
            this.prefsPrefix = DEFAULT_PREFS_PREFIX;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.domains_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new DomainsListAdapter(getActivity(), this.prefsPrefix));
    }

}
