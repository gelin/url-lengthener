package ru.gelin.lengthener;

import java.net.URI;
import java.util.regex.Pattern;

/**
 *  This processor takes the target URI from the parameter of the request.
 *  It needs two regex for URI host and path to limit processing to only some domains.
 *  Also it needs the parameter name where to get the target URI.
 *
 *  For example, to process redirects from vk.com the processor should be constructed like this:
 *  <code>
 *      UriProcessor processor = new UriFromParamProcessor(
 *          Pattern.compile("vk\\.com|.*\\.vk\\.com", Pattern.CASE_INSENSITIVE),
 *          Pattern.compile("/away.*"),
 *          "to"
 *      );
 *  </code>
 *  And for plus.url.google.com redirects from Google Plus the processor should be constructed like this:
 *  <code>
 *      UriProcessor processor = new UriFromParamProcessor(
 *          Pattern.compile("url\\.google\\.com|.*\\.url\\.google\\.com", Pattern.CASE_INSENSITIVE),
 *          Pattern.compile("/url.*"),
 *          "q"
 *      );
 *  </code>
 */
public class UriFromParamProcessor implements UriProcessor {

    private final Pattern hostPattern;
    private final Pattern pathPattern;
    private final String paramName;

    /**
     *  Creates the processor
     *  @param hostPattern  regex to match URI host
     *  @param pathPattern  regex to match URI path
     *  @param paramName    name of the URI parameter where to get the target URI
     */
    public UriFromParamProcessor(Pattern hostPattern, Pattern pathPattern, String paramName) {
        this.hostPattern = hostPattern;
        this.pathPattern = pathPattern;
        this.paramName = paramName;
    }

    @Override
    public URI process(URI uri) throws Exception {
        if (uri.getHost() == null || uri.getPath() == null) {
            return uri;
        }
        if (!this.hostPattern.matcher(uri.getHost()).matches()) {
            return uri;
        }
        if (!this.pathPattern.matcher(uri.getPath()).matches()) {
            return uri;
        }
        for (QueryParameters.Parameter parameter : new QueryParameters(uri)) {
            if (this.paramName.equals(parameter.getName())) {
                return URI.create(parameter.getValue());
            }
        }
        return uri;
    }

}
