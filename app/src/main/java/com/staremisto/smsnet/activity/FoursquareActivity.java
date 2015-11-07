package com.staremisto.smsnet.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;
import com.staremisto.smsnet.adapters.FoursquareAdapter;
import com.staremisto.smsnet.data.FourSquare;

import java.util.ArrayList;
import java.util.List;

public class FoursquareActivity extends AppCompatActivity {

    private static FoursquareActivity instance;
    private RecyclerView mRecyclerView;
    private FoursquareAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EditText searchText;
    private FrameLayout progressBar;

    public static FoursquareActivity getInstance() {
        if (instance == null)
            return new FoursquareActivity();
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foursquare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_card_list);
        mAdapter = new FoursquareAdapter(getApplicationContext(), R.layout.foursquare_card_view_item, new ArrayList<FourSquare>());
        searchText = (EditText) findViewById(R.id.edit_text_search);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        searchText.setHint("Search for Places in ".concat(prefs.getString(Constants.SHARED_PREF_CITY_FOURSQUARE_KEY, "Lviv")));

        layoutManager = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_XLARGE ? new GridLayoutManager(getApplicationContext(), 2)
                : new GridLayoutManager(getApplicationContext(), 1);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        progressBar = (FrameLayout) findViewById(R.id.progress_view);

        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_FOURSQUARE_KEY);
                            editor.apply();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                    null,
                                    Constants.ATTR_FOURSQUARE.concat((prefs.getString(Constants.SHARED_PREF_CITY_FOURSQUARE_KEY, "Lviv"))).concat("|").concat(searchText.getText().toString()),
                                    null,
                                    null);

                            progressBar.setVisibility(View.VISIBLE);

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
        List<FourSquare> list = new ArrayList<>();
        String[] result = text.split("¡");
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            String name = row[0];
            String address = row[1];
            String city = row[2];
            String checkIns = row[3];

            list.add(new FourSquare(name, address, city, checkIns));
        }

        progressBar.setVisibility(View.GONE);

        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
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
