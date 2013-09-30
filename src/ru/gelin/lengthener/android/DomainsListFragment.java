package ru.gelin.lengthener.android;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

}
