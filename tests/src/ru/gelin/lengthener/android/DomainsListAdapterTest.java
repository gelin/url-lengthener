package ru.gelin.lengthener.android;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.TextView;

public class DomainsListAdapterTest extends AndroidTestCase {

    DomainsListAdapter adapter;

    public void setUp() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.edit()
                .putString("test_domains_0", "yandex.ru")
                .putString("test_domains_1", "gelin.ru")
                .commit();
        this.adapter = new DomainsListAdapter(getContext(), "test_domains_");
    }

    public void testGetCount() {
        assertEquals(2, this.adapter.getCount());
    }

    public void testGetItem() {
        assertEquals("gelin.ru", this.adapter.getItem(0));
        assertEquals("yandex.ru", this.adapter.getItem(1));
    }

    public void testGetItemId() {
        assertEquals(0, this.adapter.getItemId(0));
        assertEquals(1, this.adapter.getItemId(1));
    }

    public void testGetView() {
        View view = this.adapter.getView(0, null, null);
        TextView text = (TextView)view.findViewById(R.id.domain);
        assertEquals("gelin.ru", text.getText());
    }

}
