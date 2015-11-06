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

        holder.tvTitle.setText(Constants.CARD_TITLES[position]);

        if (Constants.CARD_TITLES[position].equals(Constants.CARD_TITLES[0])) {
            // TODO EMERGENCY
        } else if (Constants.CARD_TITLES[position].equals(Constants.CARD_TITLES[1])) {
            Glide.with(mContext)
                    .load(R.drawable.ic_google_translate)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivTitle);
        } else if (Constants.CARD_TITLES[position].equals(Constants.CARD_TITLES[2])) {
            // TODO WIKIPEDIA
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                mContext.startActivity(intent);
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

        public CardViewViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            ivTitle = (ImageView) itemView.findViewById(R.id.iv_title);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
