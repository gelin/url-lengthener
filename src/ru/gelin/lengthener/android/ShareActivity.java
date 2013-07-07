package ru.gelin.lengthener.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import ru.gelin.lengthener.TextUrlLengthener;

/**
 *  Starts on SEND intents, replaces urls in the shared text and SEND it again.
 */
public class ShareActivity extends Activity {

    static final String TEXT_PLAIN = "text/plain";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (!Intent.ACTION_SEND.equals(intent.getAction())) {
            finish();
            return;
        }
        if (!TEXT_PLAIN.equals(intent.getType())) {
            finish();
            return;
        }

        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (text == null) {
            finish();
            return;
        }

        String newText = TextUrlLengthener.lengthenUrls(text);

        Intent newIntent = new Intent(intent);
        newIntent.putExtra(Intent.EXTRA_TEXT, newText);
        startActivity(Intent.createChooser(newIntent, getString(R.string.lengthened_urls)));

        finish();
    }

}