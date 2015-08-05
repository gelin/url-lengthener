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
 *  A base class for Fragment which allows to add and remove strings to/from the list.
 */
abstract public class StringsListFragmentBase extends ListFragment implements DialogInterface.OnClickListener {

    /** Preferences prefix parameter key */
    public static final String PREFS_PREFIX = "prefsPrefix";
    /** Add dialog title parameter key */
    public static final String ADD_DIALOG_TITLE = "addDialogTitle";

    /** Default preferences prefix */
    public static final String DEFAULT_PREFS_PREFIX = "strings_";
    /** Default add dialog title */
    public static final int DEFAULT_ADD_DIALOG_TITLE = R.string.add;

    String prefsPrefix = DEFAULT_PREFS_PREFIX;
    int addDialogTitle = DEFAULT_ADD_DIALOG_TITLE;

    EditText newString;

    ImageButton fab;

    public static StringsListFragmentBase newInstance(String prefsPrefix, int addDialogTitleRes) {
        StringsListFragmentBase fragment;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            fragment = new StringsListFragment();
        } else {
            fragment = new StringsListFragmentCompat();
        }

        Bundle args = new Bundle();
        args.putString(PREFS_PREFIX, prefsPrefix);
        args.putInt(ADD_DIALOG_TITLE, addDialogTitleRes);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.prefsPrefix = getArguments().getString(PREFS_PREFIX);
            this.addDialogTitle = getArguments().getInt(ADD_DIALOG_TITLE);
        }
        if (this.prefsPrefix == null) {
            this.prefsPrefix = DEFAULT_PREFS_PREFIX;
        }
        if (this.addDialogTitle == 0) {
            this.addDialogTitle = DEFAULT_ADD_DIALOG_TITLE;
        }
//        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.string_list_frgment, container, false);
        this.fab = (ImageButton) root.findViewById(R.id.fab);
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addString();
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setEmptyText(getString(R.string.no_domains));
    }

    void addString() {
        this.newString = new EditText(getActivity());
        Dialog dialog = new AlertDialog.Builder(getActivity())
            .setTitle(this.addDialogTitle)
            .setView(this.newString)
            .setPositiveButton(R.string.add, this)
            .setNegativeButton(android.R.string.cancel, null).create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE) {
            if (this.newString == null) {
                return;
            }
            addString(this.newString.getText().toString());
        }
    }

    void addString(String newString) {
        StringsListAdapter adapter = (StringsListAdapter)getListAdapter();
        if (adapter == null) {
            return;
        }
        adapter.addString(newString);
    }

    void deleteSelectedStrings() {
        StringsListAdapter adapter = (StringsListAdapter)getListAdapter();
        if (adapter == null) {
            return;
        }
        long[] checked = getListView().getCheckedItemIds();
        Collection<Integer> toRemove = new ArrayList<Integer>();
        for (long id : checked) {
            toRemove.add((int) id);
        }
        adapter.deleteStrings(toRemove);
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
