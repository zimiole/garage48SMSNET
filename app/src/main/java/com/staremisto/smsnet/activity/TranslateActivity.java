package com.staremisto.smsnet.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;

public class TranslateActivity extends AppCompatActivity {
    private static TranslateActivity instance;
    private EditText search;
    private TextView response;
    public static TranslateActivity getInstance() {
        if (instance == null)
            return new TranslateActivity();
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search = (EditText) findViewById(R.id.search_text);
        response = (TextView) findViewById(R.id.recieveText);
        search.setSingleLine(true);
        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_TRANSLATE_KEY);
                            editor.apply();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                    null,
                                    Constants.ATTR_TRANSLATE.concat((prefs.getString("lat", ""))).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(search.getText().toString()),
                                    null,
                                    null);

                            //progressBar.setVisibility(View.VISIBLE);

                            if (getCurrentFocus() != null) {
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        instance = this;
    }
    public void updateInfoFromSMS(String text) {

        response.setText(text);

        /*progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        searchResult.setText(text);*/

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
