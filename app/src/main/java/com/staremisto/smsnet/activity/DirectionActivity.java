package com.staremisto.smsnet.activity;

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
import android.view.View;
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
    public static DirectionActivity getInstance() {
        if (instance == null)
            return new DirectionActivity();
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_DIRECTION_KEY);
                editor.apply();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(Constants.SERVER_PHONE,
                        null,
                        Constants.ATTR_DIRECTIONS.concat(prefs.getString("lat", "")).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(searchTxt.getText().toString()).concat("|").concat("walking"),
                        null,
                        null);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.pager);
        List<PageFragment> fragments = new ArrayList<>();
        for(int i=0;i<3;i++){
            fragments.add(PageFragment.newInstance(i));
        }
        mAdapter = new PagerAdapter(getSupportFragmentManager(), DirectionActivity.this, fragments);
        viewPager.setAdapter(mAdapter);

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.direction_tabs);
        searchTxt = (EditText)findViewById(R.id.search_destination);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        break;
                    case 1:
                        if(((PageFragment)mAdapter.getItem(tabLayout.getSelectedTabPosition())).savelist ==null){
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_DIRECTION_KEY);
                            editor.apply();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                    null,
                                    Constants.ATTR_DIRECTIONS.concat(prefs.getString("lat", "")).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(searchTxt.getText().toString()).concat("|").concat("transit"),
                                    null,
                                    null);
                        }
                        break;
                    case 2:
                        if(((PageFragment)mAdapter.getItem(tabLayout.getSelectedTabPosition())).savelist ==null){
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_DIRECTION_KEY);
                            editor.apply();
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Constants.SERVER_PHONE,
                                    null,
                                    Constants.ATTR_DIRECTIONS.concat(prefs.getString("lat", "")).concat("|").concat(prefs.getString("lon", "")).concat("|").concat(searchTxt.getText().toString()).concat("|").concat("driving"),
                                    null,
                                    null);
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

        ((PageFragment)mAdapter.getItem(tabLayout.getSelectedTabPosition())).updateInfoFromSMS(text);

        /*progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        searchResult.setText(text);*/

    }

}
