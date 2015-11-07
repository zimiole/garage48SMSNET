package com.staremisto.smsnet.activity;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;

import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;
import com.staremisto.smsnet.adapters.CardViewAdapter;
import com.staremisto.smsnet.adapters.NewsCardViewAdapter;
import com.staremisto.smsnet.utils.Utility;

public class BBCActivity extends AppCompatActivity {
    private static BBCActivity instance;
    private RecyclerView mRecyclerView;
    private NewsCardViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static BBCActivity getInstance() {
        if (instance == null)
            return new BBCActivity();
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.news_card_list);
        mAdapter = new NewsCardViewAdapter(getApplicationContext(), R.layout.news_card_view_item,new String[]{});


        layoutManager = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_XLARGE ? new GridLayoutManager(getApplicationContext(), 2)
                : new GridLayoutManager(getApplicationContext(), 1);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_BBC_KEY);
        editor.apply();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(Constants.SERVER_PHONE,
                null,
                Constants.ATTR_BBC,
                null,
                null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = this;
    }

    public void updateInfoFromSMS(String text) {
        mAdapter.setTitles(text.split("¡"));
        mAdapter.notifyDataSetChanged();
        /*progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.VISIBLE);
        searchResult.setText(text);*/

    }

}
