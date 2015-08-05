package ru.gelin.lengthener.android;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

/**
 *  A Fragment which allows to add and remove strings to/from the list.
 *  This version of the fragment works on devices with API < 11
 */
public class StringsListFragmentCompat extends StringsListFragmentBase implements DialogInterface.OnClickListener {

    ActionMode actionMode;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new StringsListAdapterCompat(getActivity(), this.prefsPrefix));

        final ListView list = getListView();
        final StringsListAdapterCompat adapter = (StringsListAdapterCompat)getListAdapter();
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                adapter.toggleChecked(position);
//                list.setItemChecked(position, true);
                if (StringsListFragmentCompat.this.actionMode == null) {
                    ((ActionBarActivity) getActivity()).startSupportActionMode(new ActionModeCallback());
                } else {
                    int checkedCount = adapter.getCheckedCount();
                    if (checkedCount == 0) {
                        StringsListFragmentCompat.this.actionMode.finish();
                    } else {
                        StringsListFragmentCompat.this.actionMode.setTitle(String.valueOf(checkedCount));
                    }
                }
            }
        });
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            onStartActionMode();
            StringsListFragmentCompat.this.actionMode = actionMode;
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.string_list_context, menu);
            StringsListAdapterCompat adapter = (StringsListAdapterCompat)getListAdapter();
            actionMode.setTitle(String.valueOf(adapter.getCheckedCount()));
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;   //nothing to do
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_delete:
                    deleteSelectedStrings();
                    actionMode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            StringsListFragmentCompat.this.actionMode = null;
            StringsListAdapterCompat adapter = (StringsListAdapterCompat)getListAdapter();
            adapter.clearChecked();
            onFinishActionMode();
        }
    }

    void deleteSelectedStrings() {
        StringsListAdapterCompat adapter = (StringsListAdapterCompat)getListAdapter();
        if (adapter == null) {
            return;
        }
        Collection<Integer> toRemove = new ArrayList<Integer>();
        synchronized (adapter) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.isChecked(i)) {
                    toRemove.add(i);
                }
            }
            adapter.deleteStrings(toRemove);
        }
    }

}
