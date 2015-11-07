package com.staremisto.smsnet.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.R;
import com.staremisto.smsnet.activity.DetailActivity;
import com.staremisto.smsnet.activity.WikipediaActivity;

/**
 * Created by orodr_000 on 07.11.2015.
 */

public class NewsCardViewAdapter extends RecyclerView.Adapter<NewsCardViewAdapter.CardViewViewHolder> {

    protected  String[] titles;
    protected Context mContext;
    protected int layout;


    public NewsCardViewAdapter(Context context, int layout,String[] titles) {
        mContext = context;
        this.layout = layout;
        this.titles = titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
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



        holder.tvTitle.setText(titles[position]);


    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {


        public TextView tvTitle;
        public CardView cardView;

        public CardViewViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.news_card_view);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
