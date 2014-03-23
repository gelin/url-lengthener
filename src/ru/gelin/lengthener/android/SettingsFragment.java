package ru.gelin.lengthener.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class SettingsFragment extends ListFragment {

    static final String SELECTED_ITEM_KEY = "selectedItem";
    static final int SELECTED_ITEM_DEFAULT = 0;
    int selectedItem = -1;

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
        if (getResources().getBoolean(R.bool.dual_pane)) {
            if (savedInstanceState != null) {
                onListItemClick(getListView(), savedInstanceState.getInt(SELECTED_ITEM_KEY, SELECTED_ITEM_DEFAULT));
            } else {
                onListItemClick(getListView(), SELECTED_ITEM_DEFAULT);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_KEY, this.selectedItem);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        onListItemClick(listView, position);
    }

    void onListItemClick(ListView listView, int position) {
        if (this.selectedItem == position) {
            return;
        }
        SettingsListAdapter adapter = (SettingsListAdapter)getListAdapter();
        SettingsListAdapter.SettingsListItem item = adapter.getSettingsItem(position);
        if (item == null) {
            return;
        }
        if (getResources().getBoolean(R.bool.dual_pane)) {
            listView.setItemChecked(position, true);
            showFragment(item.fragment);
        } else {
            startIntent(item.intent);
        }
        this.selectedItem = position;
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
