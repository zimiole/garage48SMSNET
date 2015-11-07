package com.staremisto.smsnet.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;
import com.staremisto.smsnet.adapters.WeatherAdapter;
import com.staremisto.smsnet.data.Weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {
    private static WeatherActivity instance;
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private FrameLayout progressBar;

    public static WeatherActivity getInstance() {
        if (instance == null)
            return new WeatherActivity();
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (FrameLayout) findViewById(R.id.progress_view);
        List<Weather> list = new ArrayList<>();
        adapter = new WeatherAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, Constants.SHARED_PREF_WEATHER_KEY);
        editor.apply();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(Constants.SERVER_PHONE,
                null,
                Constants.ATTR_WEATHER.concat(prefs.getString(Constants.SHARED_PREF_CITY_KEY, "Lviv")),
                null,
                null);

        progressBar.setVisibility(View.VISIBLE);

        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        instance = this;
    }

    public void updateInfoFromSMS(String text) {
        List<Weather> list = new ArrayList<>();
        String[] result = text.split("¡");
        for (int i = 0; i < result.length; i++) {
            String[] row = result[i].split(";");
            String temperature = row[0];
            String weatherCondition = row[1];
            String timeOfDay = null;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat simpleShortDateFormat = new SimpleDateFormat("hh:mm");
            Date date = null;
            try {
                date = simpleDateFormat.parse(row[2]);
                timeOfDay = simpleShortDateFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            list.add(new Weather(temperature, weatherCondition, "", timeOfDay));
        }

        progressBar.setVisibility(View.GONE);

        adapter.setWeatherList(list);
        adapter.notifyDataSetChanged();
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
