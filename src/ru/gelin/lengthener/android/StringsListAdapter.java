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
 *  A ListAdapter which reads the list of strings from SharedPreferences.
 */
public class StringsListAdapter extends BaseAdapter {

    final String prefsPrefix;
    final Context context;
    final List<String> strings = new ArrayList<String>();

    public StringsListAdapter(Context context, String prefsPrefix) {
        this.prefsPrefix = prefsPrefix;
        this.context = context;
        readStrings();
    }

    synchronized void readStrings() {
        this.strings.clear();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        int i = 0;
        Set<String> strings = new TreeSet<String>();
        while (prefs.contains(key(i))) {
            strings.add(prefs.getString(key(i), ""));
            i++;
        }
        this.strings.addAll(strings);
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
        return this.strings.size();
    }

    @Override
    public Object getItem(int position) {
        return this.strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflateView(parent);
        }
        bindView(view, position);
        return view;
    }

    View inflateView(ViewGroup parent) {
        return LayoutInflater.from(this.context).inflate(R.layout.string_list_item, parent, false);
    }

    void bindView(View view, int position) {
        TextView text = (TextView) view.findViewById(R.id.string);
        text.setText(this.strings.get(position));
    }

    public synchronized void addString(String newString) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key(getCount()), newString);
        editor.commit();
        readStrings();
        notifyDataSetChanged();
    }

    public synchronized Set<String> getStrings() {
        Set<String> result = new TreeSet<String>();
        result.addAll(this.strings);
        return result;
    }

    public synchronized void deleteStrings(Collection<Integer> positions) {
        int origSize = this.strings.size();
        List<String> toRemove = new ArrayList<String>();
        for (int position : positions) {
            toRemove.add(this.strings.get(position));
        }
        for (String string : toRemove) {
            this.strings.remove(string);
        }
        saveAllStrings(origSize);
        readStrings();
        notifyDataSetChanged();
    }

    private synchronized void saveAllStrings(int origSize) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor editor = prefs.edit();
        for (int i = 0; i < this.strings.size(); i++) {
            editor.putString(key(i), this.strings.get(i));
        }
        for (int j = this.strings.size(); j < origSize; j++) {
            editor.remove(key(j));
        }
        editor.commit();
    }

}
