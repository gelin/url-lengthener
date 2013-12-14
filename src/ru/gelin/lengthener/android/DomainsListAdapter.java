package ru.gelin.lengthener.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.*;

/**
 *  A ListAdapter which reads the list of domain names from SharedPreferences.
 */
public class DomainsListAdapter extends BaseAdapter {

    final String prefsPrefix;
    final Context context;
    final List<String> domains = new ArrayList<String>();

    public DomainsListAdapter(Context context, String prefsPrefix) {
        this.prefsPrefix = prefsPrefix;
        this.context = context;
        readDomains();
    }

    synchronized void readDomains() {
        this.domains.clear();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        int i = 0;
        Set<String> domains = new TreeSet<String>();
        while (prefs.contains(key(i))) {
            domains.add(prefs.getString(key(i), ""));
            i++;
        }
        this.domains.addAll(domains);
    }

    String key(int index) {
        return this.prefsPrefix + index;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return this.domains.size();
    }

    @Override
    public Object getItem(int i) {
        return this.domains.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflateView(parent);
        }
        bindView(view, i);
        return view;
    }

    View inflateView(ViewGroup parent) {
        return LayoutInflater.from(this.context).inflate(R.layout.domain_list_item, parent, false);
    }

    void bindView(View view, int i) {
        TextView text = (TextView) view.findViewById(R.id.domain);
        text.setText(this.domains.get(i));
    }

    public synchronized void addDomain(String newDomain) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key(getCount()), newDomain);
        editor.commit();
        readDomains();
        notifyDataSetChanged();
    }

    public synchronized Set<String> getDomains() {
        Set<String> result = new TreeSet<String>();
        result.addAll(this.domains);
        return result;
    }

    public synchronized void deleteDomains(Collection<Integer> positions) {
        List<String> toRemove = new ArrayList<String>();
        for (int position : positions) {
            toRemove.add(this.domains.get(position));
        }
        for (String domain : toRemove) {
            this.domains.remove(domain);
        }
        saveAllDomains();
        readDomains();
    }

    private synchronized void saveAllDomains() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor editor = prefs.edit();
        for (int i = 0; i > this.domains.size(); i++) {
            editor.putString(key(i), this.domains.get(i));
        }
        editor.remove(key(this.domains.size()));
        editor.commit();
    }

}
