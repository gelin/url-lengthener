package ru.gelin.lengthener;

import android.test.AndroidTestCase;

import java.net.URI;
import java.util.regex.Pattern;

public class UriFromParamProcessorTest extends AndroidTestCase {

    public void testGPlusParameterExtraction() throws Exception {
        UriProcessor processor = new UriFromParamProcessor(
                Pattern.compile("url\\.google\\.com|.*\\.url\\.google\\.com", Pattern.CASE_INSENSITIVE),
                Pattern.compile("/url.*"),
                "q");
        URI result = processor.process(URI.create(
                "http://plus.url.google.com/url?q=http://www.androidauthority.com/google-duo-693188/?utm_source%3Ddlvr.it%26utm_medium%3Dgplus&rct=j&ust=1463671453316000&usg=AFQjCNEdJDH9Ot6NbxWsPvCV_Barcdbr6Q"));
        assertEquals(URI.create("http://www.androidauthority.com/google-duo-693188/?utm_source=dlvr.it&utm_medium=gplus"),
                result);
    }

}
