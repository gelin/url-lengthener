package ru.gelin.lengthener;

import android.test.AndroidTestCase;
import ru.gelin.lengthener.UrlLengthener;

import java.io.IOException;

public class UrlLengthenerTest extends AndroidTestCase {

    public void testGooGl() throws IOException {
        assertEquals("http://ya.ru/", UrlLengthener.lengthenUrl("http://goo.gl/Krvqj"));
    }

    public void testBitLy() throws IOException {
        assertEquals("http://ya.ru/", UrlLengthener.lengthenUrl("http://bit.ly/WQuM"));
    }

}
