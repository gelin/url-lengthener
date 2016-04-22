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
        long start = System.currentTimeMillis();
        UrlLengthener.lengthenUrl("http://feedproxy.google.com/~r/ct_news/~3/JCG5eeyTxZo/story01.htm");
        long end = System.currentTimeMillis();
        assertTrue("Too long execution", (end - start) < ((RedirectProcessor.CONNECT_TIMEOUT + RedirectProcessor.READ_TIMEOUT) * 2));
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

    public void testGooGlNoNetwork() throws IOException {
        assertEquals("http://goo.gl/Krvqj", UrlLengthener.lengthenUrl("http://goo.gl/Krvqj", TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

    public void testRemoveQueryNoNetwork() throws IOException {
        assertEquals("http://www.linux.org.ru/news/openoffice/9393827",
                UrlLengthener.lengthenUrl(
                        "http://www.linux.org.ru/news/openoffice/9393827?abc=def&utm_source=qwerty&ghi=nbv",
                        TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

    public void testRemoveParamsNoNetwork() throws IOException {
        assertEquals("http://example.com/?abc=def&ghi=nbv",
                UrlLengthener.lengthenUrl(
                        "http://example.com/?abc=def&utm_source=qwerty&ghi=nbv",
                        TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

    public void testVKNoNetwork() {
        assertEquals("http://apriorilingva55.ru/",
                UrlLengthener.lengthenUrl("https://vk.com/away.php?to=http%3A%2F%2Fapriorilingva55.ru%2F%3Futm_source%3Dvk%26utm_medium%3Dsocial%26utm_campaign%3Dreferal&post=-31121583_4369",
                        TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

    public void testMVKNoNetwork() {
        assertEquals("http://apriorilingva55.ru/",
                UrlLengthener.lengthenUrl("https://m.vk.com/away.php?to=http%3A%2F%2Fapriorilingva55.ru%2F%3Futm_source%3Dvk%26utm_medium%3Dsocial%26utm_campaign%3Dreferal&post=-31121583_4369",
                        TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

    public void testMVKAwayNoNetwork() {
        assertEquals("http://apriorilingva55.ru/",
                UrlLengthener.lengthenUrl("https://m.vk.com/away?to=http%3A%2F%2Fapriorilingva55.ru%2F%3Futm_source%3Dvk%26utm_medium%3Dsocial%26utm_campaign%3Dreferal&post=-31121583_4369",
                        TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

}
