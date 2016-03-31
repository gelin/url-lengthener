package ru.gelin.lengthener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 *  Lengthens one URL
 */
public class UrlLengthener {

    static final String USER_AGENT = "URL Lengthener";

    public static String lengthenUrl(String url) throws IOException {
        return lengthenUrl(url, DefaultLengthenerSettings.INSTANCE);
    }

    public static String lengthenUrl(String url, LengthenerSettings settings) throws IOException {
        return new Lengthener(settings).lengthenUrl(url);
    }

    private UrlLengthener() {
        //avoid instantiation
    }

    private static class Lengthener {

        LengthenerSettings settings;

        Lengthener(LengthenerSettings settings) {
            this.settings = settings;
        }

        public String lengthenUrl(String url) throws IOException {
            URI uri = URI.create(url);
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            try {
                connection.addRequestProperty("User-Agent", USER_AGENT);
                connection.setInstanceFollowRedirects(true);
                InputStream input = connection.getInputStream();    //just to initiate the redirects
                input.close();
                uri = connection.getURL().toURI();
            } catch (Exception e) {
                //if we failed to connect, keep next processing
            } finally {
                connection.disconnect();
            }
            uri = removeQuery(uri);
            uri = removeParams(uri);
            return uri.toString();
        }

        URI removeQuery(URI uri) {
            String host = uri.getHost();
            if (this.settings == null || this.settings.getRemoveQueryDomains() == null
                    || this.settings.getRemoveQueryDomains().isEmpty()) {
                return uri;
            }
            for (String domain : this.settings.getRemoveQueryDomains()) {
                if (host.equals(domain) || host.endsWith("." + domain)) {
                    return removeQueryPart(uri);
                }
            }
            return uri;
        }

        URI removeQueryPart(URI uri) {
            try {
                return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(),
                        uri.getPort(), uri.getPath(), null, uri.getFragment());
            } catch (URISyntaxException e) {
                return uri;
            }
        }

        URI removeParams(URI uri) throws IOException {
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

}
