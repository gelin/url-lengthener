package ru.gelin.lengthener.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

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

    ImageButton fab;

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
//        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.domain_list_frgment, container, false);
        this.fab = (ImageButton) root.findViewById(R.id.fab);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDomain();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setEmptyText(getString(R.string.no_domains));
    }

    void addDomain() {
        this.newDomain = new EditText(getActivity());
        Dialog dialog = new AlertDialog.Builder(getActivity())
            .setTitle(R.string.add_domain)
            .setView(this.newDomain)
            .setPositiveButton(R.string.add, this)
            .setNegativeButton(android.R.string.cancel, null).create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
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

    protected void onStartActionMode() {
        if (this.fab == null) {
            return;
        }
        this.fab.setVisibility(View.GONE);
    }

    protected void onFinishActionMode() {
        if (this.fab == null) {
            return;
        }
        this.fab.setVisibility(View.VISIBLE);
    }

}
