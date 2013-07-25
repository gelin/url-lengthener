package ru.gelin.lengthener;

import java.util.ArrayList;
import java.util.List;

enum TestLengthenerSettings implements LengthenerSettings {

    INSTANCE;

    @Override
    public List<String> getRemoveQueryDomains() {
        List<String> result = new ArrayList<String>();
        result.add("linux.org.ru");
        return result;
    }

}
