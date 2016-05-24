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
        assertTrue("Too long execution", (end - start) < ((RedirectProcessor.CONNECT_TIMEOUT + RedirectProcessor.READ_TIMEOUT) * 2));
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

    public void testVK() {
        assertEquals("VK: http://apriorilingva55.ru/",
                TextUrlLengthener.lengthenUrls("VK: https://vk.com/away.php?to=http%3A%2F%2Fapriorilingva55.ru%2F%3Futm_source%3Dvk%26utm_medium%3Dsocial%26utm_campaign%3Dreferal&post=-31121583_4369",
                        TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

    public void testGPlus() {
        assertEquals("G+: http://appsconf.ru",
                TextUrlLengthener.lengthenUrls("G+: http://plus.url.google.com/url?q=http://appsconf.ru&rct=j&ust=1461772113919000&usg=AFQjCNGr60h5A0GeUuoh5OhP25GskDjwAQ",
                        TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

    public void testGPlus2() {
        assertEquals("G+: http://www.yaplakal.com/forum1/topic1367710.html",
                TextUrlLengthener.lengthenUrls("G+: http://plus.url.google.com/url?q=http://www.yaplakal.com/forum1/topic1367710.html&rct=j&ust=1461940843242000&usg=AFQjCNFGw0r7wCS0nSRYmNzyWolCfebo1Q",
                        TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

    public void testGPlus3() {
        assertEquals("G+: http://www.androidauthority.com/google-duo-693188/",
                TextUrlLengthener.lengthenUrls("G+: http://plus.url.google.com/url?q=http://www.androidauthority.com/google-duo-693188/?utm_source%3Ddlvr.it%26utm_medium%3Dgplus&rct=j&ust=1463671453316000&usg=AFQjCNEdJDH9Ot6NbxWsPvCV_Barcdbr6Q",
                        TestLengthenerSettings.INSTANCE_NO_NETWORK));
    }

}
