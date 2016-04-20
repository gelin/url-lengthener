package ru.gelin.lengthener;

import java.net.URI;

/**
 *  This processor works for https://vk.com/away.php redirects.
 *  The away.php page uses JS and HTML to redirect.
 *  But the redirection target is put into 'to' url parameter.
 *  This processor takes the 'to' parameter and returns it's value.
 */
public class VkAwayProcessor implements UriProcessor {

    @Override
    public URI process(URI uri) throws Exception {
        if (!"vk.com".equalsIgnoreCase(uri.getHost())) {
            return uri;
        }
        if (!"/away.php".equals(uri.getPath())) {
            return uri;
        }
        for (QueryParameters.Parameter parameter : new QueryParameters(uri)) {
            if ("to".equals(parameter.getName())) {
                return URI.create(parameter.getValue());
            }
        }
        return uri;
    }

}
