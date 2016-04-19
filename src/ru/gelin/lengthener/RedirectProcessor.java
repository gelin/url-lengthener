package ru.gelin.lengthener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;

/**
 *  Processes URI by doing a real HTTP request and following all redirects.
 *  This processor requires network access,
 *  if a network is not available, it skips processing.
 */
public class RedirectProcessor implements UriProcessor {

    static final String USER_AGENT = "URL Lengthener";
    static final int CONNECT_TIMEOUT = 5000;    // 5 secs
    static final int READ_TIMEOUT = 10000;      // 10 secs

    private final LengthenerSettings settings;

    /**
     *  Constructs the processor.
     *  @param settings lengthener settings to check network availability
     */
    public RedirectProcessor(LengthenerSettings settings) {
        this.settings = settings;
    }

    @Override
    public URI process(URI uri) throws Exception {
        if (this.settings != null && !this.settings.isNetworkAvailable()) {
            return uri;
        }
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        try {
            connection.addRequestProperty("User-Agent", USER_AGENT);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            InputStream input = connection.getInputStream();    //just to initiate the redirects
            input.close();
            uri = connection.getURL().toURI();
        } finally {
            connection.disconnect();
        }
        return uri;
    }

}
