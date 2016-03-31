package ru.gelin.lengthener;

import android.test.AndroidTestCase;

import java.io.IOException;

public class UrlLengthenerTest extends AndroidTestCase {

    public void testGooGl() throws IOException {
        assertEquals("https://ya.ru/", UrlLengthener.lengthenUrl("http://goo.gl/Krvqj"));
    }

    public void testBitLy() throws IOException {
        assertEquals("https://ya.ru/", UrlLengthener.lengthenUrl("http://bit.ly/WQuM"));
    }

    public void testFeedProxy() throws IOException {
        assertEquals("http://www.computerra.ru/73419/paradoks-kontsentratsii-pochemu-deyatelnost-v-sfere-informatsionnyih-tehnologiy-styagivaetsya-k-stolitsam-nesmotrya-na-ih-dorogoviznu/",
                UrlLengthener.lengthenUrl("http://feedproxy.google.com/~r/ct_news/~3/JCG5eeyTxZo/story01.htm"));
    }

    public void testFeedsPortal() throws IOException {
        assertEquals("http://www.computerra.ru/73618/choose-a-camera-for-summer-holidays/",
                UrlLengthener.lengthenUrl("http://rss.feedsportal.com/c/32137/f/413387/s/2e4731a3/l/0L0Scomputerra0Bru0C736180Cchoose0Ea0Ecamera0Efor0Esummer0Eholidays0C/story01.htm"));
    }

    public void testRemoveQuery() throws IOException {
        assertEquals("http://www.linux.org.ru/news/openoffice/9393827",
                UrlLengthener.lengthenUrl(
                        "http://feedproxy.google.com/~r/org/LOR/~3/PehIerjNU5k/9393827",
                        TestLengthenerSettings.INSTANCE));
    }

    public void testRemoveParams() throws IOException {
        assertEquals("http://example.com/?abc=def&ghi=nbv",
                UrlLengthener.lengthenUrl(
                        "http://example.com/?abc=def&utm_source=qwerty&ghi=nbv",
                        TestLengthenerSettings.INSTANCE));
    }

    public void testRemoveParamsEncoded() throws IOException {
        // why not this? "http://example.com/?%D0%B9%D1%86%D1%83=%D0%BA%D0%B5%D0%BD%D0%B3&%D1%84%D1%8B%D0%B2%D0%B0=%D0%BE%D0%BB%D0%B4%D0%B6"
        assertEquals("http://example.com/?йцу=кенг&фыва=олдж",
                UrlLengthener.lengthenUrl(
                        "http://example.com/?%D0%B9%D1%86%D1%83=%D0%BA%D0%B5%D0%BD%D0%B3&utm_source=%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80&%D1%84%D1%8B%D0%B2%D0%B0=%D0%BE%D0%BB%D0%B4%D0%B6",
                        TestLengthenerSettings.INSTANCE));
    }

    public void testRemoveMultiplePatterns() throws IOException {
        assertEquals("http://example.com/?abc=def&ghi=nbv",
                UrlLengthener.lengthenUrl(
                        "http://example.com/?abc=def&utm_source=qwerty&testABC=test&ghi=nbv",
                        TestLengthenerSettings.INSTANCE));
    }

    public void testTailingQuestionMark() throws IOException {
        assertEquals("http://example.com/",
                UrlLengthener.lengthenUrl(
                        "http://example.com/?utm_source=qwerty&testABC=test",
                        TestLengthenerSettings.INSTANCE));
    }

}
