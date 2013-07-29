package ru.gelin.lengthener;

import java.util.Collections;
import java.util.Set;

/**
 *  Default settings for Lengthener.
 */
enum DefaultLengthenerSettings implements LengthenerSettings {

    INSTANCE;

    @Override
    public Set<String> getRemoveQueryDomains() {
        return Collections.emptySet();
    }

}
