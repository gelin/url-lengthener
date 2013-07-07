package ru.gelin.lengthener.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import ru.gelin.lengthener.TextUrlLengthener;

/**
 *  Starts on SEND intents, replaces urls in the shared text and SEND it again.
 */
public class ShareActivity extends Activity {

    static final String TEXT_PLAIN = "text/plain";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress);

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

        new LengthenTask().execute(text);
    }

    class LengthenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return TextUrlLengthener.lengthenUrls(strings[0]);
        }

        @Override
        protected void onPostExecute(String text) {
            Intent newIntent = new Intent(getIntent());
            newIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(newIntent, getString(R.string.lengthened_urls)));
            finish();
        }

    }

}