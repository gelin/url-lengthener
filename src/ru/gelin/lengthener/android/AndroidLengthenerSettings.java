package ru.gelin.lengthener.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import ru.gelin.lengthener.LengthenerSettings;

import java.util.Set;

import static android.content.Context.CONNECTIVITY_SERVICE;

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


    /**
     *  Check availability of network connections.
     *  Returns true if any network connection is available.
     */
    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager)this.context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        return info.isAvailable();
    }

}
