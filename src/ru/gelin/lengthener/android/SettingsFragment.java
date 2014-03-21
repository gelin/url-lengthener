package ru.gelin.lengthener.android;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class SettingsFragment extends ListFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new SettingsListAdapter(getActivity()));
        ListView listView = getListView();
        listView.setDividerHeight(0);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
//        Toast.makeText(getActivity(), "selected " + listView.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();
//        if (position == listView.getCheckedItemPosition()) {
//            return;
//        }
        SettingsListAdapter adapter = (SettingsListAdapter)getListAdapter();
        SettingsListAdapter.SettingsListItem item = adapter.getSettingsItem(position);
        if (getResources().getBoolean(R.bool.dual_pane)) {
            showFragment(item.fragment);
        } else {
            startIntent(item.intent);
        }
        listView.setItemChecked(position, true);
    }

    void showFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_details, fragment)
                .commit();
    }

    void startIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        startActivity(intent);
    }

}
