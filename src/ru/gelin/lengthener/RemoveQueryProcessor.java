package ru.gelin.lengthener;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *  This processor removes query part for some domains.
 *  It uses {@link LengthenerSettings#getRemoveQueryDomains()} to get the list of domains for which to remove the query.
 *  A network connection is not required for this processor.
 */
public class RemoveQueryProcessor implements UriProcessor {

    private final LengthenerSettings settings;

    /**
     *  Creates the processor.
     *  @param settings settings to get the list of domains to process
     */
    public RemoveQueryProcessor(LengthenerSettings settings) {
        this.settings = settings;
    }

    @Override
    public URI process(URI uri) throws Exception {
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

    private URI removeQueryPart(URI uri) {
        try {
            return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(),
                    uri.getPort(), uri.getPath(), null, uri.getFragment());
        } catch (URISyntaxException e) {
            return uri;
        }
    }

}
