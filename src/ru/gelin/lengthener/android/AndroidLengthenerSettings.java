package ru.gelin.lengthener.android;

import android.content.Context;
import ru.gelin.lengthener.LengthenerSettings;

import java.util.Set;

public class AndroidLengthenerSettings implements LengthenerSettings {

    public static String REMOVE_QUERY_DOMAINS_PREFIX = "remove_query_domains_";

    private final Context context;

    public AndroidLengthenerSettings(Context context) {
        this.context = context;
    }

    @Override
    public Set<String> getRemoveQueryDomains() {
        DomainsListAdapter adapter = new DomainsListAdapter(this.context, REMOVE_QUERY_DOMAINS_PREFIX);
        return adapter.getDomains();
    }

}
