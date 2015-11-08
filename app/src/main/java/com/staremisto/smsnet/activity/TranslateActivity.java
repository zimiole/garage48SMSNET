package com.staremisto.smsnet.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;

import java.util.Locale;

public class TranslateActivity extends AppCompatActivity {
    private static TranslateActivity instance;
    private EditText search;
    private TextView response;
    private TextView fromlanguage;
    private ImageView close;
    private ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(TranslateActivity.this,R.style.MyTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        close = (ImageView) findViewById(R.id.search_close_btn);
        close.setVisibility(View.INVISIBLE);
        fromlanguage = (TextView) findViewById(R.id.from_language);
        fromlanguage.setText(Locale.getDefault().getDisplayLanguage());
        search = (EditText) findViewById(R.id.search_text);
        response = (TextView) findViewById(R.id.recieveText);
        search.setSingleLine(true);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                close.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
            }
        });
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
                            progressDialog.show();

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
        progressDialog.dismiss();
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
