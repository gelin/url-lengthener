package ru.gelin.lengthener;

import java.util.HashSet;
import java.util.Set;

enum TestLengthenerSettings implements LengthenerSettings {

    INSTANCE(true),
    INSTANCE_NO_NETWORK(false);

    private final boolean networkAvailable;

    TestLengthenerSettings(boolean networkAvailable) {
        this.networkAvailable = networkAvailable;
    }

    @Override
    public Set<String> getRemoveQueryDomains() {
        Set<String> result = new HashSet<String>();
        result.add("linux.org.ru");
        return result;
    }

    @Override
    public Set<String> getRemoveParamPatterns() {
        Set<String> result = new HashSet<String>();
        result.add("utm_*");
        result.add("*ABC");
        return result;
    }

    @Override
    public boolean isNetworkAvailable() {
        return this.networkAvailable;
    }

}
