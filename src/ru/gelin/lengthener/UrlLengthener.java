package ru.gelin.lengthener;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 *  Lengthens one URL
 */
public class UrlLengthener {

    public static String lengthenUrl(String url) {
        return lengthenUrl(url, DefaultLengthenerSettings.INSTANCE);
    }

    public static String lengthenUrl(String url, LengthenerSettings settings) {
        return new Lengthener(settings).lengthenUrl(url);
    }

    private UrlLengthener() {
        //avoid instantiation
    }

    private static class Lengthener {

        List<UriProcessor> processors = new ArrayList<UriProcessor>(3);

        Lengthener(LengthenerSettings settings) {
            processors.add(new RedirectProcessor(settings));
            processors.add(new RemoveQueryProcessor(settings));
            processors.add(new RemoveParamsProcessor(settings));
        }

        public String lengthenUrl(String url) {
            URI uri = URI.create(url);
            for (UriProcessor processor : this.processors) {
                try {
                    uri = processor.process(uri);
                } catch (Exception e) {
                    // keep silence
                }
            }
            return uri.toString();
        }

    }

}
