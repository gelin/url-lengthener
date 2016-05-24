package ru.gelin.lengthener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Finds URL in the text, lengthen all them.
 */
public class TextUrlLengthener {

    //http://mattheworiordan.tumblr.com/post/13174566389/url-regular-expression-for-links-with-or-without-the
    static final Pattern URL_PATTERN = Pattern.compile(
            "(http(s)?:\\/\\/)" +                   // protocol
            "[A-Za-z0-9\\.\\-]+" +                  // domain
            "(" +                                   // optional part (
            "(?:\\/[\\+~%\\/\\.\\w\\-_]*)?" +       // /path
            "\\??(?:[\\-\\+=&;%@\\.\\w_:/!\\?]*)" + // ?query
            "#?(?:[\\.\\!\\/\\\\w=%&\\-]*)" +       // #anchor
            ")?");                                  // ) end of optional part

    public static String lengthenUrls(String text) {
        return lengthenUrls(text, DefaultLengthenerSettings.INSTANCE);
    }

    public static String lengthenUrls(String text, LengthenerSettings settings) {
        Matcher m = URL_PATTERN.matcher(text);
        StringBuffer result = new StringBuffer();
        while (m.find()) {
            String url = m.group();
            url = UrlLengthener.lengthenUrl(url, settings);
            m.appendReplacement(result, url);
        }
        m.appendTail(result);
        return result.toString();
    }

    private TextUrlLengthener() {
        //avoid instantiation
    }

}
