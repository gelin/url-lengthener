package ru.gelin.lengthener;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 *  This processor removes some query params from URI.
 *  The list of params to remove is taken from {@link LengthenerSettings#getRemoveParamPatterns()}.
 *  This processor doesn't require network access.
 */
public class RemoveParamsProcessor implements UriProcessor {

    private final LengthenerSettings settings;

    /**
     *  Creates the processor.
     *  @param settings settings to get the list of params
     */
    public RemoveParamsProcessor(LengthenerSettings settings) {
        this.settings = settings;
    }

    @Override
    public URI process(URI uri) throws Exception {
        if (this.settings == null || this.settings.getRemoveParamPatterns() == null
                || this.settings.getRemoveParamPatterns().isEmpty()) {
            return uri;
        }
        if (uri.getQuery() == null || uri.getQuery().length() == 0) {
            return uri;
        }

        List<Glob> globs = new ArrayList<Glob>(this.settings.getRemoveParamPatterns().size());
        for (String pattern : this.settings.getRemoveParamPatterns()) {
            try {
                globs.add(new Glob(pattern));
            } catch (Exception e) {
                //invalid pattern
            }
        }

        String[] pairs = uri.getQuery().split("&");
        StringBuilder newQuery = new StringBuilder();
        pairs:
        for (String pair : pairs) {
            int equalSignIndex = pair.indexOf("=");
            String name = equalSignIndex > 0 ?
                    URLDecoder.decode(pair.substring(0, equalSignIndex), "utf-8") :
                    pair;
            for (Glob glob : globs) {
                if (glob.matches(name)) {
                    continue pairs;
                }
            }
            if (newQuery.length() > 0) {
                newQuery.append("&");
            }
            newQuery.append(name);
            if (equalSignIndex > 0 && pair.length() > equalSignIndex + 1) {
                String value = URLDecoder.decode(pair.substring(equalSignIndex + 1), "utf-8");
                newQuery.append("=");
                newQuery.append(value);
            }
        }

        try {
            return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(),
                    uri.getPort(), uri.getPath(),
                    newQuery.length() > 0 ? newQuery.toString() : null,
                    uri.getFragment());
        } catch (URISyntaxException e) {
            return uri;
        }
    }

}
