package ru.gelin.lengthener.android;

import android.content.Context;
import ru.gelin.lengthener.LengthenerSettings;

import java.util.Set;

/**
 *  Lengthener settings taken from the android application preferences.
 */
public class AndroidLengthenerSettings implements LengthenerSettings {

    public static final String REMOVE_QUERY_DOMAINS_PREFIX = "remove_query_domains_";
    public static final String REMOVE_PARAMS_PATTERNS_PREFIX = "remove_params_patterns_";

    private final Context context;

    public AndroidLengthenerSettings(Context context) {
        this.context = context;
    }

    @Override
    public Set<String> getRemoveQueryDomains() {
        StringsListAdapter adapter = new StringsListAdapter(this.context, REMOVE_QUERY_DOMAINS_PREFIX);
        return adapter.getStrings();
    }

    @Override
    public Set<String> getRemoveParamPatterns() {
        StringsListAdapter adapter = new StringsListAdapter(this.context, REMOVE_PARAMS_PATTERNS_PREFIX);
        return adapter.getStrings();
    }

}
