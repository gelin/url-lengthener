package ru.gelin.lengthener;

import java.util.Collections;
import java.util.List;

/**
 *  Default settings for Lengthener.
 */
enum DefaultLengthenerSettings implements LengthenerSettings {

    INSTANCE;

    @Override
    public List<String> getRemoveQueryDomains() {
        return Collections.emptyList();
    }

}
