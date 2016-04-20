package ru.gelin.lengthener;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Iterator;

/**
 *  Iterates over URI query parameters.
 */
public class QueryParameters implements Iterable<QueryParameters.Parameter> {

    public static class Parameter {
        private String name;
        private String value;

        public Parameter(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return this.name;
        }

        public String getValue() {
            return this.value;
        }
    }

    private final String[] pairs;

    public QueryParameters(URI uri) {
        String query = uri.getQuery();
        if (query == null || query.length() == 0) {
            this.pairs = new String[] {};   //empty array
        } else {
            this.pairs = uri.getQuery().split("&");
        }
    }

    @Override
    public Iterator<Parameter> iterator() {
        return new ParameterIterator();
    }

    private class ParameterIterator implements Iterator<Parameter> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return this.index < QueryParameters.this.pairs.length;
        }

        @Override
        public Parameter next() {
            String pair = QueryParameters.this.pairs[index];
            int equalSignIndex = pair.indexOf("=");
            String name = null;
            String value = null;
            try {
                name = equalSignIndex > 0 ?
                        URLDecoder.decode(pair.substring(0, equalSignIndex), "utf-8") :
                        pair;
                if (equalSignIndex > 0 && pair.length() > equalSignIndex + 1) {
                    value = URLDecoder.decode(pair.substring(equalSignIndex + 1), "utf-8");
                }
            } catch (UnsupportedEncodingException e) {
                // pass with nulls
            }
            this.index++;
            return new Parameter(name, value);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }
    }

}
