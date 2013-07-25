package ru.gelin.lengthener;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *  Lengthen one URL
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
                    return removeQuery(uri);
                }
            }
            return uri;
        }

        URI removeQuery(URI uri) {
            try {
                return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(),
                        uri.getPort(), uri.getPath(), null, uri.getFragment());
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
