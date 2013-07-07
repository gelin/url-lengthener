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

    public void testFeedProxy() throws IOException {
        assertEquals("http://www.computerra.ru/73419/paradoks-kontsentratsii-pochemu-deyatelnost-v-sfere-informatsionnyih-tehnologiy-styagivaetsya-k-stolitsam-nesmotrya-na-ih-dorogoviznu/",
                UrlLengthener.lengthenUrl("http://feedproxy.google.com/~r/ct_news/~3/JCG5eeyTxZo/story01.htm"));
    }

    public void testFeedsPortal() throws IOException {
        assertEquals("http://www.computerra.ru/73618/choose-a-camera-for-summer-holidays/",
                UrlLengthener.lengthenUrl("http://rss.feedsportal.com/c/32137/f/413387/s/2e4731a3/l/0L0Scomputerra0Bru0C736180Cchoose0Ea0Ecamera0Efor0Esummer0Eholidays0C/story01.htm"));
    }

}
