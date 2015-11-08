package com.staremisto.smsnet.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;
import com.staremisto.smsnet.adapters.DirectionsCardViewAdapter;
import com.staremisto.smsnet.adapters.NewsCardViewAdapter;
import com.staremisto.smsnet.adapters.PagerAdapter;
import com.staremisto.smsnet.data.RouteInstruction;
import com.staremisto.smsnet.fragments.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class DirectionActivity extends AppCompatActivity {
    private static DirectionActivity instance;
    ViewPager viewPager;
    private EditText searchTxt;
    TabLayout tabLayout;
    private PagerAdapter mAdapter;
    private ProgressDialog progressDialog;
    public static DirectionActivity getInstance() {
        if (instance == null)
            return new DirectionActivity();
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        progressDialog = new ProgressDialog(DirectionActivity.this,R.style.MyTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewPager = (ViewPager) findViewById(R.id.pager);
        final List<PageFragment> fragments = new ArrayList<>();
        for(int i=0;i<3;i++){
            fragments.add(PageFragment.newInstance(i));
        }
        mAdapter = new PagerAdapter(getSupportFragmentManager(), DirectionActivity.this, fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.direction_tabs);
        tabLayout.setVisibility(View.INVISIBLE);
        viewPager.setVisibility(View.INVISIBLE);
        searchTxt = (EditText)findViewById(R.id.search_destination);
        searchTxt.setSingleLine(true);
        searchTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_DIRECTION_KEY);
                            editor.apply();
                            SmsManager smsManager = SmsManager.getDefault();
                            switch(viewPager.getCurrentItem()){
                                case 0:
                                    smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                            null,
                                            Constants.ATTR_DIRECTIONS.concat(prefs.getString("lat", "")).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(searchTxt.getText().toString()).concat("|").concat("w"),
                                            null,
                                            null);
                                    break;
                                case 1:
                                    smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                            null,
                                            Constants.ATTR_DIRECTIONS.concat(prefs.getString("lat", "")).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(searchTxt.getText().toString()).concat("|").concat("t"),
                                            null,
                                            null);
                                    break;
                                case 2:
                                    smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                            null,
                                            Constants.ATTR_DIRECTIONS.concat(prefs.getString("lat", "")).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(searchTxt.getText().toString()).concat("|").concat("d"),
                                            null,
                                            null);
                                    break;
                            }

                            progressDialog.show();

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
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        if(fragments.get(tab.getPosition()).savelist.size()<=0){
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_DIRECTION_KEY);
                            editor.apply();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                    null,
                                    Constants.ATTR_DIRECTIONS.concat(prefs.getString("lat", "")).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(searchTxt.getText().toString()).concat("|").concat("w"),
                                    null,
                                    null);
                            progressDialog.show();
                        }
                        break;
                    case 1:
                        if(fragments.get(tab.getPosition()).savelist.size()<=0){
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_DIRECTION_KEY);
                            editor.apply();
                            SmsManager smsManager = SmsManager.getDefault();
                            Log.d("Message",searchTxt.getText().toString());
                            smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                    null,
                                    Constants.ATTR_DIRECTIONS.concat(prefs.getString("lat", "")).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(searchTxt.getText().toString()).concat("|").concat("t"),
                                    null,
                                    null);
                            progressDialog.show();
                        }
                        break;
                    case 2:
                        if((fragments.get(tab.getPosition()).savelist.size())<=0){
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_DIRECTION_KEY);
                            editor.apply();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                    null,
                                    Constants.ATTR_DIRECTIONS.concat(prefs.getString("lat", "")).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(searchTxt.getText().toString()).concat("|").concat("d"),
                                    null,
                                    null);
                            progressDialog.show();
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = this;
    }
    public void updateInfoFromSMS(String text) {
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
                ((PageFragment) mAdapter.getItem(viewPager.getCurrentItem())).updateInfoFromSMS(text);

        /*progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        searchResult.setText(text);*/

    }

}
