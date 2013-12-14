package ru.gelin.lengthener.android;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
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
        if (!isNetworkAvailable()) {
            passThrough(intent);
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

    /**
     *  Check availability of network connections.
     *  Returns true if any network connection is available.
     */
    boolean isNetworkAvailable() {
        ConnectivityManager manager =
                (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        return info.isAvailable();
    }

    /**
     *  Pass the original intent through without modification.
     */
    void passThrough(Intent intent) {
        Toast.makeText(this, R.string.cannot_lengthen, Toast.LENGTH_LONG).show();
        startActivity(Intent.createChooser(intent, getString(R.string.not_lengthened_urls)));
    }

    class LengthenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return TextUrlLengthener.lengthenUrls(strings[0], new AndroidLengthenerSettings(ShareActivity.this));
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