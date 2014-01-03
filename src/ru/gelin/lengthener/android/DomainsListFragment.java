package ru.gelin.lengthener.android;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

/**
 *  A PreferenceFragment which allows to add and remove domains (as strings) to/from the list.
 */
public class DomainsListFragment extends ListFragment implements DialogInterface.OnClickListener {

    /** Preferences prefix parameter key */
    public static final String PREFS_PREFIX = "prefsPrefix";
    /** Default preferences prefix */
    public static final String DEFAULT_PREFS_PREFIX = "domains_";

    String prefsPrefix = DEFAULT_PREFS_PREFIX;

    EditText newDomain;

    public static DomainsListFragment newInstance(String prefsPrefix) {
        DomainsListFragment fragment = new DomainsListFragment();

        Bundle args = new Bundle();
        args.putString(PREFS_PREFIX, prefsPrefix);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.prefsPrefix = getArguments().getString(PREFS_PREFIX);
        }
        if (this.prefsPrefix == null) {
            this.prefsPrefix = DEFAULT_PREFS_PREFIX;
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new DomainsListAdapter(getActivity(), this.prefsPrefix));

        final ListView list = getListView();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setMultiChoiceModeListener(new ChoiceListener());
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                list.setItemChecked(position, true);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.domains_action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addDomain();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void addDomain() {
        this.newDomain = new EditText(getActivity());
        new AlertDialog.Builder(getActivity())
            .setTitle(R.string.add_domain)
            .setView(this.newDomain)
            .setPositiveButton(R.string.add, this)
            .setNegativeButton(android.R.string.cancel, null).create().show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE) {
            if (this.newDomain == null) {
                return;
            }
            addDomain(this.newDomain.getText().toString());
        }
    }

    void addDomain(String newDomain) {
        DomainsListAdapter adapter = (DomainsListAdapter)getListAdapter();
        if (adapter == null) {
            return;
        }
        adapter.addDomain(newDomain);
    }

    void deleteSelectedDomains() {
        DomainsListAdapter adapter = (DomainsListAdapter)getListAdapter();
        if (adapter == null) {
            return;
        }
        long[] checked = getListView().getCheckedItemIds();
        Collection<Integer> toRemove = new ArrayList<Integer>();
        for (long id : checked) {
            toRemove.add((int) id);
        }
        adapter.deleteDomains(toRemove);
    }

    private class ChoiceListener implements AbsListView.MultiChoiceModeListener {

        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
            actionMode.setTitle(String.valueOf(getListView().getCheckedItemCount()));
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.domains_context, menu);
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
            //nothing to do
        }
    }

}
