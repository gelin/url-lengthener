package ru.gelin.lengthener;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
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
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", USER_AGENT);
        HttpContext context = new BasicHttpContext();
        client.execute(request, context);
        HttpHost target = (HttpHost)context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
        HttpUriRequest finalRequest = (HttpUriRequest)context.getAttribute(ExecutionContext.HTTP_REQUEST);
        URI finalUri = finalRequest.getURI();
        try {
            return String.valueOf(URIUtils.rewriteURI(finalUri, target));
        } catch (URISyntaxException e) {
            throw new IOException("got invalid URI: " + finalUri);
        }
    }

    private UrlLengthener() {
        //avoid instantiation
    }

}
