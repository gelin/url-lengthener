package ru.gelin.lengthener;

import android.test.AndroidTestCase;

import java.io.IOException;

public class TextUrlLengthenerTest extends AndroidTestCase {

    public void testGooGl() {
        assertEquals("Yandex: http://ya.ru/", TextUrlLengthener.lengthenUrls("Yandex: http://goo.gl/Krvqj"));
    }

    public void testBitLy() {
        assertEquals("Yandex: http://ya.ru/", TextUrlLengthener.lengthenUrls("Yandex: http://bit.ly/WQuM"));
    }

    public void testFeedProxy() {
        assertEquals("Computerra: http://www.computerra.ru/73419/paradoks-kontsentratsii-pochemu-deyatelnost-v-sfere-informatsionnyih-tehnologiy-styagivaetsya-k-stolitsam-nesmotrya-na-ih-dorogoviznu/",
                TextUrlLengthener.lengthenUrls("Computerra: http://feedproxy.google.com/~r/ct_news/~3/JCG5eeyTxZo/story01.htm"));
    }

    public void testFeedsPortal() {
        assertEquals("Computerra: http://www.computerra.ru/73618/choose-a-camera-for-summer-holidays/",
                TextUrlLengthener.lengthenUrls("Computerra: http://rss.feedsportal.com/c/32137/f/413387/s/2e4731a3/l/0L0Scomputerra0Bru0C736180Cchoose0Ea0Ecamera0Efor0Esummer0Eholidays0C/story01.htm"));
    }

    public void testMultipleUrls() {
        assertEquals("Yandex: http://ya.ru/\nAnother Yandex: http://ya.ru/",
                TextUrlLengthener.lengthenUrls("Yandex: http://goo.gl/Krvqj\nAnother Yandex: http://bit.ly/WQuM"));
    }

    public void testRemoveQuery() throws IOException {
        assertEquals("LOR: http://www.linux.org.ru/news/openoffice/9393827",
                TextUrlLengthener.lengthenUrls(
                        "LOR: http://feedproxy.google.com/~r/org/LOR/~3/PehIerjNU5k/9393827",
                        TestLengthenerSettings.INSTANCE));
    }

}
