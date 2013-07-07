package ru.gelin.lengthener;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Finds URL in the text, lengthen all them.
 */
public class TextUrlLengthener {

    //http://mattheworiordan.tumblr.com/post/13174566389/url-regular-expression-for-links-with-or-without-the
    static Pattern URL_PATTERN = Pattern.compile("((http(s)?:\\/\\/)[A-Za-z0-9.-]+((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[.\\!\\/\\\\w]*))?)");

    public static String lengthenUrls(String text) {
        Matcher m = URL_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            String url = m.group();
            try {
                url = UrlLengthener.lengthenUrl(url);
            } catch (IOException e) {
                //ignoring possible errors
            }
            m.appendReplacement(result, url);
        }
        m.appendTail(result);
        return result.toString();
    }

    private TextUrlLengthener() {
        //avoid instantiation
    }

}
