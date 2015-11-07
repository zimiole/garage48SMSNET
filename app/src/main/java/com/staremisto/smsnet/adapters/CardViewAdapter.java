package com.staremisto.smsnet.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;
import com.staremisto.smsnet.activity.BBCActivity;
import com.staremisto.smsnet.activity.DetailActivity;
import com.staremisto.smsnet.activity.DirectionActivity;
import com.staremisto.smsnet.activity.FoursquareActivity;
import com.staremisto.smsnet.activity.WeatherActivity;
import com.staremisto.smsnet.activity.WikipediaActivity;


/**
 * Created by Oleh on 9/9/2015.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewViewHolder> {

    protected final String[] titles;
    protected Context mContext;
    protected int layout;

    public CardViewAdapter(Context context, int layout) {
        mContext = context;
        this.layout = layout;
        titles = Constants.CARD_TITLES;
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

        holder.tvTitle.setText(Constants.CARD_TITLES[position]);

        if (Constants.CARD_TITLES[position].equals(Constants.CARD_TITLES[0])) {
            Glide.with(mContext)
                    .load(R.drawable.ic_bbc)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivTitle);

            intent = new Intent(mContext, BBCActivity.class);
        } else if (Constants.CARD_TITLES[position].equals(Constants.CARD_TITLES[1])) {
            Glide.with(mContext)
                    .load(R.drawable.ic_google_translate)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivTitle);

            intent = new Intent(mContext, DetailActivity.class);
        } else if (Constants.CARD_TITLES[position].equals(Constants.CARD_TITLES[2])) {
            Glide.with(mContext)
                    .load(R.drawable.ic_wikipedia)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivTitle);

            intent = new Intent(mContext, WikipediaActivity.class);
        } else if (Constants.CARD_TITLES[position].equals(Constants.CARD_TITLES[3])) {
            Glide.with(mContext)
                    .load(R.drawable.art_clear)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivTitle);

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

        }else if (Constants.CARD_TITLES[position].equals(Constants.CARD_TITLES[4])) {


            intent = new Intent(mContext, DirectionActivity.class);
        } else if (Constants.CARD_TITLES[position].equals(Constants.CARD_TITLES[5])) {

            Glide.with(mContext)
                    .load(R.drawable.ic_foursquare)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivTitle);

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
        }


        final Intent finalIntent = intent;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(finalIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Constants.CARD_TITLES.length;
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivTitle;
        public TextView tvTitle;
        public CardView cardView;
        public Toolbar toolbar;

        public CardViewViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            ivTitle = (ImageView) itemView.findViewById(R.id.iv_title);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            toolbar = (Toolbar) itemView.findViewById(R.id.toolbar);
        }
    }
}
