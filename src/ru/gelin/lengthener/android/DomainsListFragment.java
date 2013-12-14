package ru.gelin.lengthener.android;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.AbsListView;
import android.widget.Button;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.domains_list, container, false);
        Button addButton = (Button)result.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDomain();
            }
        });
        return result;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new DomainsListAdapter(getActivity(), this.prefsPrefix));

        ListView list = getListView();
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list.setMultiChoiceModeListener(new ChoiceListener());
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
        Collection<Integer> toRemove = new ArrayList<Integer>();
        SparseBooleanArray positions = getListView().getCheckedItemPositions();
        for (int i = 0; i < positions.size(); i++) {
            if (positions.get(i)) {
                toRemove.add(i);
            }
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
