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

/**
 *  A Fragment which allows to add and remove domains (as strings) to/from the list.
 *  This version of the fragment works on devices with API < 11
 */
public class DomainsListFragmentCompat extends DomainsListFragmentBase implements DialogInterface.OnClickListener {

    ActionMode actionMode;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ListView list = getListView();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (DomainsListFragmentCompat.this.actionMode == null) {
                    ((ActionBarActivity) getActivity()).startSupportActionMode(new ActionModeCallback());
                } else {
                    DomainsListFragmentCompat.this.actionMode.setTitle(String.valueOf(getCheckedItemCount(list)));
                }
                list.setItemChecked(position, true);
            }
        });
    }

    int getCheckedItemCount(ListView list) {
        long[] checked = list.getCheckedItemIds();
        if (checked != null) {
            return checked.length;
        } else {
            return 0;
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            DomainsListFragmentCompat.this.actionMode = actionMode;
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.domains_context, menu);
            actionMode.setTitle(String.valueOf(getCheckedItemCount(getListView())));
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
                    deleteSelectedDomains();
                    actionMode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            DomainsListFragmentCompat.this.actionMode = null;
        }
    }

}
