package com.staremisto.smsnet.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;

public class WikipediaActivity extends AppCompatActivity {

    private static WikipediaActivity instance;
    private CardView cardView;
    private EditText searchText;
    private TextView searchResult;
    private FrameLayout progressBar;

    public static WikipediaActivity getInstance() {
        if (instance == null)
            return new WikipediaActivity();
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wikipedia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardView = (CardView) findViewById(R.id.card_view);
        searchText = (EditText) findViewById(R.id.edit_text_search);
        searchResult = (TextView) findViewById(R.id.tv_text);
        progressBar = (FrameLayout) findViewById(R.id.progress_view);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_WIKIPEDIA_KEY);
                    editor.apply();

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(Constants.SERVER_PHONE,
                            null,
                            Constants.ATTR_WIKIPEDIA.concat(searchText.getText().toString()),
                            null,
                            null);

                    progressBar.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);

                    if (getCurrentFocus() != null) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        instance = this;
    }

    public void updateInfoFromSMS(String text) {
        progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        searchResult.setText(Html.fromHtml(text));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
