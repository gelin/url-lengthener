package ru.gelin.lengthener.android;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 *  A ListAdapter which reads the list of strings from SharedPreferences.
 *  This version contains additional methods to handle checking of multiple items.
 */
public class StringsListAdapterCompat extends StringsListAdapter {

    List<Boolean> checked;

    public StringsListAdapterCompat(Context context, String prefsPrefix) {
        super(context, prefsPrefix);
    }

    @Override
    synchronized void readStrings() {
        super.readStrings();
        this.checked = new ArrayList<Boolean>();
        for (String string : this.strings) {
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
