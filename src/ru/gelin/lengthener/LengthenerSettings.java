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

}
