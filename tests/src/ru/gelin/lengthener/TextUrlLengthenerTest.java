package ru.gelin.lengthener;

import android.test.AndroidTestCase;

import java.io.IOException;

public class TextUrlLengthenerTest extends AndroidTestCase {

    public void testGooGl() {
        assertEquals("Yandex: https://ya.ru/", TextUrlLengthener.lengthenUrls("Yandex: http://goo.gl/Krvqj"));
    }

    public void testBitLy() {
        assertEquals("Yandex: https://ya.ru/", TextUrlLengthener.lengthenUrls("Yandex: http://bit.ly/WQuM"));
    }

    public void testFeedProxy() {
        long start = System.currentTimeMillis();
        TextUrlLengthener.lengthenUrls("Computerra: http://feedproxy.google.com/~r/ct_news/~3/JCG5eeyTxZo/story01.htm");
        long end = System.currentTimeMillis();
        assertTrue("Too long execution", (end - start) < (RedirectProcessor.CONNECT_TIMEOUT * 2.5));
    }

    public void testMultipleUrls() {
        assertEquals("Yandex: https://ya.ru/\nAnother Yandex: https://ya.ru/",
                TextUrlLengthener.lengthenUrls("Yandex: http://goo.gl/Krvqj\nAnother Yandex: http://bit.ly/WQuM"));
    }

    public void testRemoveQuery() throws IOException {
        assertEquals("LOR: http://www.linux.org.ru/news/openoffice/9393827",
                TextUrlLengthener.lengthenUrls(
                        "LOR: http://feedproxy.google.com/~r/org/LOR/~3/PehIerjNU5k/9393827",
                        TestLengthenerSettings.INSTANCE));
    }

}
