package ru.gelin.lengthener.android;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *  A list adapter to represent list of settings.
 */
public class SettingsListAdapter extends BaseAdapter {

    public static final int PREFERENCE_VIEW = 0;
    public static final int HEADER_VIEW = 1;
    public static final int VIEW_TYPE_COUNT = 2;

    public static class SettingsListItem {
        public final int viewType;
        public final int title;
        public final int summary;
        public final Intent intent;
        public final Fragment fragment;
        SettingsListItem(int title) {
            this.viewType = HEADER_VIEW;
            this.title = title;
            this.summary = 0;
            this.intent = null;
            this.fragment = null;
        }
        SettingsListItem(int title, int summary, Intent intent, Fragment fragment) {
            this.viewType = PREFERENCE_VIEW;
            this.title = title;
            this.summary = summary;
            this.intent = intent;
            this.fragment = fragment;
        }
    }

    private static SettingsListItem[] ITEMS = {
        new SettingsListItem(R.string.how_to_use, R.string.app_description,
                new Intent("ru.gelin.lengthener.android.ACTION_HELP"),
                HelpFragment.newInstance()),
        new SettingsListItem(R.string.domains),
        new SettingsListItem(R.string.remove_query, R.string.remove_query_for_domains,
                new Intent("ru.gelin.lengthener.android.ACTION_SET_REMOVE_QUERY_DOMAINS"),
                DomainsListFragment.newInstance(AndroidLengthenerSettings.REMOVE_QUERY_DOMAINS_PREFIX)),
    };

    private final Context context;

    public SettingsListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return ITEMS.length;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    SettingsListItem getSettingsItem(int position) {
        return ITEMS[position];
    }

    @Override
    public Object getItem(int position) {
        return getSettingsItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return getSettingsItem(position).viewType;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        SettingsListItem item = getSettingsItem(position);
        if (view == null) {
            view = inflateView(item, viewGroup);
        }
        bindView(view, item);
        return view;
    }

    View inflateView(SettingsListItem item, ViewGroup viewGroup) {
        int layout = 0;
        switch (item.viewType) {
            case PREFERENCE_VIEW:
                layout = R.layout.big_summary_preference;
                break;
            case HEADER_VIEW:
                layout = android.R.layout.preference_category;
                break;
        }
        return LayoutInflater.from(this.context).inflate(layout, viewGroup, false);
    }

    void bindView(View view, SettingsListItem item) {
        TextView title = null;
        TextView summary = null;
        switch (item.viewType) {
            case PREFERENCE_VIEW:
                view.setClickable(true);
                view.setFocusable(true);
                title = (TextView)view.findViewById(android.R.id.title);
                summary = (TextView)view.findViewById(android.R.id.summary);
                break;
            case HEADER_VIEW:
                view.setClickable(false);
                view.setFocusable(false);
                title = (TextView)view.findViewById(android.R.id.title);
                break;
        }
        if (title != null) {
            title.setText(item.title);
        }
        if (summary != null) {
            summary.setText(item.summary);
        }
    }

}
