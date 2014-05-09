package ru.gelin.lengthener.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.*;

/**
 *  A ListAdapter which reads the list of domain names from SharedPreferences.
 *  This version contains additional methods to handle checking of multiple items.
 */
public class DomainsListAdapterCompat extends DomainsListAdapter {

    List<Boolean> checked;

    public DomainsListAdapterCompat(Context context, String prefsPrefix) {
        super(context, prefsPrefix);
    }

    @Override
    synchronized void readDomains() {
        super.readDomains();
        this.checked = new ArrayList<Boolean>();
        for (String domain : this.domains) {
            this.checked.add(false);
        }
    }

    public synchronized void setChecked(int position, boolean checked) {
        this.checked.set(position, checked);
    }

    public synchronized void toggleChecked(int position) {
        setChecked(position, !isChecked(position));
    }

    public synchronized void clearChecked() {
        for (int i = 0; i < this.checked.size(); i++) {
            this.checked.set(i, false);
        }
        notifyDataSetChanged();
    }

    public synchronized boolean isChecked(int position) {
        return this.checked.get(position);
    }

    public synchronized int getCheckedCount() {
        int count = 0;
        for (boolean checked : this.checked) {
            if (checked) {
                count++;
            }
        }
        return count;
    }

    @Override
    void bindView(View view, int position) {
        super.bindView(view, position);
        if (isChecked(position)) {
            view.setBackgroundResource(R.drawable.list_highlight);
        } else {
            view.setBackgroundResource(R.drawable.list_selector_background);
        }
    }

}
