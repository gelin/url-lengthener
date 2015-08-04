package ru.gelin.lengthener;

import android.test.AndroidTestCase;

public class GlobTest extends AndroidTestCase {

    public void testUtm() {
        Glob glob = new Glob("utm_*");
        assertTrue(glob.matches("utm_source"));
        assertTrue(glob.matches("utm_medium"));
        assertTrue(glob.matches("utm_campaign"));
        assertTrue(glob.matches("utm_anything_else"));
        assertFalse(glob.matches("not_utm"));
        assertFalse(glob.matches("not_utm_source"));
    }

    public void testStartAsterisk() {
        Glob glob = new Glob("*test");
        assertTrue(glob.matches("something_test"));
        assertTrue(glob.matches("somethingtest"));
        assertFalse(glob.matches("abc_test_def"));
        assertFalse(glob.matches("test_def"));
    }

    public void testMidAsterisk() {
        Glob glob = new Glob("te*st");
        assertTrue(glob.matches("te_something_st"));
        assertTrue(glob.matches("test"));
        assertFalse(glob.matches("something_test_something"));
    }

    public void testQuestion() {
        Glob glob = new Glob("t??t");
        assertTrue(glob.matches("test"));
        assertTrue(glob.matches("text"));
        assertTrue(glob.matches("t__t"));
        assertFalse(glob.matches("_t__t_"));
        assertFalse(glob.matches("teest"));
    }

    public void testConvertGlobToRegex() {
        assertEquals("utm_.*", Glob.convertGlobToRegex("utm_*"));
        assertEquals(".*test.*", Glob.convertGlobToRegex("*test*"));
        assertEquals("te.*st", Glob.convertGlobToRegex("te*st"));
        assertEquals("t..t", Glob.convertGlobToRegex("t??t"));
    }

}
