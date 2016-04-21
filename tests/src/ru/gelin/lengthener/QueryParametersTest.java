package ru.gelin.lengthener;

import android.test.AndroidTestCase;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class QueryParametersTest extends AndroidTestCase {

    public void testParseParameters() {
        URI uri = URI.create("https://example.com/?abc=def&abc=asd&zxc=poi");
        List<QueryParameters.Parameter> results = new ArrayList<QueryParameters.Parameter>();
        for (QueryParameters.Parameter p : new QueryParameters(uri)) {
            results.add(p);
        }
        assertEquals(3, results.size());
        assertEquals("abc", results.get(0).getName());
        assertEquals("def", results.get(0).getValue());
        assertEquals("abc", results.get(1).getName());
        assertEquals("asd", results.get(1).getValue());
        assertEquals("zxc", results.get(2).getName());
        assertEquals("poi", results.get(2).getValue());
    }

    public void testParseEncodedParameters() {
        URI uri = URI.create("http://example.com/?%D0%B9%D1%86%D1%83=%D0%BA%D0%B5%D0%BD%D0%B3&%D1%84%D1%8B%D0%B2%D0%B0=%D0%BE%D0%BB%D0%B4%D0%B6");
        List<QueryParameters.Parameter> results = new ArrayList<QueryParameters.Parameter>();
        for (QueryParameters.Parameter p : new QueryParameters(uri)) {
            results.add(p);
        }
        assertEquals(2, results.size());
        assertEquals("йцу", results.get(0).getName());
        assertEquals("кенг", results.get(0).getValue());
        assertEquals("фыва", results.get(1).getName());
        assertEquals("олдж", results.get(1).getValue());
    }

    public void testEmptyParameters() {
        URI uri = URI.create("http://example.com/?");
        for (QueryParameters.Parameter p : new QueryParameters(uri)) {
            fail();
        }
    }

    public void testNoParameters() {
        URI uri = URI.create("http://example.com");
        for (QueryParameters.Parameter p : new QueryParameters(uri)) {
            fail();
        }
    }

}
