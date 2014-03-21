package ru.gelin.lengthener.android;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;

public class SettingsFragment extends ListFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new SettingsListAdapter(getActivity()));
        getListView().setDividerHeight(0);
    }

//    @Override
//    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
//        String key = preference.getKey();
//        if ("help".equals(key)) {
//            showFragment(HelpFragment.newInstance());
//            return true;
//        } else if ("remove_query_domains".equals(key)) {
//            showFragment(DomainsListFragment.newInstance(AndroidLengthenerSettings.REMOVE_QUERY_DOMAINS_PREFIX));
//            return true;
//        } else {
//            return super.onPreferenceTreeClick(preferenceScreen, preference);
//        }
//    }

    void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_details, fragment)
                .commit();
    }

}
