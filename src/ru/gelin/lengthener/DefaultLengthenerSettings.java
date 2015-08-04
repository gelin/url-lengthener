package ru.gelin.lengthener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *  Default settings for Lengthener.
 */
enum DefaultLengthenerSettings implements LengthenerSettings {

    INSTANCE;

    static final Set<String> DEFAULT_REMOVE_PARAM_PATTERNS = new HashSet<String>();
    static {
        DEFAULT_REMOVE_PARAM_PATTERNS.add("utm_*");
    }

    @Override
    public Set<String> getRemoveQueryDomains() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getRemoveParamPatterns() {
        return Collections.unmodifiableSet(DEFAULT_REMOVE_PARAM_PATTERNS);
    }

}
