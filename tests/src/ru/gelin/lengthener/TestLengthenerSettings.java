package ru.gelin.lengthener;

import java.util.HashSet;
import java.util.Set;

enum TestLengthenerSettings implements LengthenerSettings {

    INSTANCE;

    @Override
    public Set<String> getRemoveQueryDomains() {
        Set<String> result = new HashSet<String>();
        result.add("linux.org.ru");
        return result;
    }

}
