package ru.gelin.lengthener;

import java.util.List;

/**
 *  The lengthener settings to be passed to the lengthener methods.
 */
public interface LengthenerSettings {

    /**
     *  Returns the list of domains for which to remove query part of URLs.
     */
    List<String> getRemoveQueryDomains();

}
