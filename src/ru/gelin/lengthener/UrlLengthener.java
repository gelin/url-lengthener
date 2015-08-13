package ru.gelin.lengthener;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Lengthens one URL
 */
public class UrlLengthener {

    static final String USER_AGENT = "URL Lengthener";

    static private HttpClient client = new DefaultHttpClient();

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
            HttpGet request = new HttpGet(url);
            request.setHeader("User-Agent", USER_AGENT);
            HttpContext context = new BasicHttpContext();
            client.execute(request, context);
            HttpHost target = (HttpHost)context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
            HttpUriRequest finalRequest = (HttpUriRequest)context.getAttribute(ExecutionContext.HTTP_REQUEST);
            URI finalUri = finalRequest.getURI();
            finalUri = removeQuery(finalUri, target);
            finalUri = removeParams(finalUri);
            return toString(finalUri, target);
        }

        URI removeQuery(URI uri, HttpHost host) {
            if (this.settings == null || this.settings.getRemoveQueryDomains() == null
                    || this.settings.getRemoveQueryDomains().isEmpty()) {
                return uri;
            }
            String hostName = host.getHostName();
            for (String domain : this.settings.getRemoveQueryDomains()) {
                if (hostName.equals(domain) || hostName.endsWith("." + domain)) {
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

        URI removeParams(URI uri) {
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

            List<NameValuePair> params = URLEncodedUtils.parse(uri, "UTF-8");
            StringBuilder newQuery = new StringBuilder();
            params:
            for (NameValuePair pair : params) {
                for (Glob glob : globs) {
                    if (glob.matches(pair.getName())) {
                        continue params;
                    }
                }
                if (newQuery.length() > 0) {
                    newQuery.append("&");
                }
                newQuery.append(pair.getName());
                newQuery.append("=");
                newQuery.append(pair.getValue());
            }

            try {
                return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(),
                        uri.getPort(), uri.getPath(),
                        newQuery.toString(),
                        uri.getFragment());
            } catch (URISyntaxException e) {
                return uri;
            }
        }

        String toString(URI uri, HttpHost host) throws IOException {
            try {
                return String.valueOf(URIUtils.rewriteURI(uri, host));
            } catch (URISyntaxException e) {
                throw new IOException("got invalid URI: " + uri);
            }
        }

    }

}
