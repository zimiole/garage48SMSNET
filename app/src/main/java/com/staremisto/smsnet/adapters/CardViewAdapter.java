package com.staremisto.smsnet.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;
import com.staremisto.smsnet.activity.DirectionActivity;
import com.staremisto.smsnet.activity.FoursquareActivity;
import com.staremisto.smsnet.activity.TranslateActivity;
import com.staremisto.smsnet.activity.WeatherActivity;
import com.staremisto.smsnet.activity.WikipediaActivity;


/**
 * Created by Oleh on 9/9/2015.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewViewHolder> {

    private final String[] titles = {
            "Food & Drink",
            "Public Transportation",
            "Information request",
            "Weather Forecast",
            "Language"
    };

    private final String[] descriptions = {
            "Foursquare",
            "Google Maps",
            "Wikipedia",
            "AccuWeather",
            "Google Translate"
    };

    protected Context mContext;
    protected int layout;

    public CardViewAdapter(Context context, int layout) {
        mContext = context;
        this.layout = layout;
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(layout, parent, false);
        mContext = parent.getContext();
        return new CardViewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardViewViewHolder holder, final int position) {

        Intent intent = null;

        // set TextViews
        holder.tvTitle.setText(titles[position]);
        holder.tvDescription.setText(descriptions[position]);

        switch (position) {
            case 0:
                holder.ivTitle.setImageResource(R.drawable.ic_foursquare_white_36dp);

                holder.toolbar.inflateMenu(R.menu.menu_card_weather);
                holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Choose Location");
                        final EditText input = new EditText(mContext);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        input.setHint("Choose City");
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                        input.setText(prefs.getString(Constants.SHARED_PREF_CITY_FOURSQUARE_KEY, "Lviv"));
                        builder.setView(input);
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!TextUtils.isEmpty(input.getText().toString())) {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(Constants.SHARED_PREF_CITY_FOURSQUARE_KEY, input.getText().toString());
                                    editor.apply();
                                    Toast.makeText(mContext, "City changed to ".concat(input.getText().toString()), Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                        return true;
                    }
                });

                intent = new Intent(mContext, FoursquareActivity.class);
                break;
            case 1:
                holder.ivTitle.setImageResource(R.drawable.ic_map_icon);
                intent = new Intent(mContext, DirectionActivity.class);
                break;
            case 2:
                holder.ivTitle.setImageResource(R.drawable.ic_wikipedia_white_36dp);
                intent = new Intent(mContext, WikipediaActivity.class);
                break;
            case 3:
                holder.ivTitle.setImageResource(R.drawable.ic_weather_partlycloudy_white_36dp);

                holder.toolbar.inflateMenu(R.menu.menu_card_weather);
                holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Choose Location");
                        final EditText input = new EditText(mContext);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        input.setHint("Choose City");
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                        input.setText(prefs.getString(Constants.SHARED_PREF_CITY_KEY, "Lviv"));
                        builder.setView(input);
                        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!TextUtils.isEmpty(input.getText().toString())) {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(Constants.SHARED_PREF_CITY_KEY, input.getText().toString());
                                    editor.apply();
                                    Toast.makeText(mContext, "City changed to ".concat(input.getText().toString()), Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                        return true;
                    }
                });

                intent = new Intent(mContext, WeatherActivity.class);
                break;
            case 4:
                holder.ivTitle.setImageResource(R.drawable.ic_translate_icon);
                intent = new Intent(mContext, TranslateActivity.class);
                break;
            default:
                break;
        }


        final Intent finalIntent = intent;
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(finalIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivTitle;
        public TextView tvTitle;
        public TextView tvDescription;
        public LinearLayout linearLayout;
        public Toolbar toolbar;

        public CardViewViewHolder(View itemView) {
            super(itemView);

            ivTitle = (ImageView) itemView.findViewById(R.id.iv_title);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.ll_row);
            toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
        }
    }
}
