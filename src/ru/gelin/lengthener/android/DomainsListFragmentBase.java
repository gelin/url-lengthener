package ru.gelin.lengthener.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collection;

/**
 *  A base class for Fragment which allows to add and remove domains (as strings) to/from the list.
 */
abstract public class DomainsListFragmentBase extends ListFragment implements DialogInterface.OnClickListener {

    /** Preferences prefix parameter key */
    public static final String PREFS_PREFIX = "prefsPrefix";
    /** Default preferences prefix */
    public static final String DEFAULT_PREFS_PREFIX = "domains_";

    String prefsPrefix = DEFAULT_PREFS_PREFIX;

    EditText newDomain;

    public static DomainsListFragmentBase newInstance(String prefsPrefix) {
        DomainsListFragmentBase fragment;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            fragment = new DomainsListFragment();
        } else {
            fragment = new DomainsListFragmentCompat();
        }

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

}
