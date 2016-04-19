package ru.gelin.lengthener;

import java.net.URI;

/**
 *  A common interface for an URL processing stage.
 */
interface UriProcessor {

    /**
     *  Processes the input URI in some way and returns a new URI.
     *  If processing is not necessary, must return the same URI.
     */
    URI process(URI uri) throws Exception;

}
