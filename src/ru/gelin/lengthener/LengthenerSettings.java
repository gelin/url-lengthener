package ru.gelin.lengthener;

import java.util.Set;

/**
 *  The lengthener settings to be passed to the lengthener methods.
 */
public interface LengthenerSettings {

    /**
     *  Returns the list of domains for which to remove query part of URLs.
     */
    Set<String> getRemoveQueryDomains();

    /**
     *  Return the list of parameter glob patterns which to remove from query part of URLs.
     *  For example, to remove UTM parameters: http://www.koozai.com/blog/analytics/utm-parameters/
     */
    Set<String> getRemoveParamPatterns();

    /**
     *  Returns true if network is available to resolve redirects.
     *  Returns false if network is not available, so operations which require it should be skipped.
     */
    boolean isNetworkAvailable();

}
