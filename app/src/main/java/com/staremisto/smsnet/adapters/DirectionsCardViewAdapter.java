package com.staremisto.smsnet.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.staremisto.smsnet.R;
import com.staremisto.smsnet.data.RouteInstruction;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by orodr_000 on 07.11.2015.
 */
public class DirectionsCardViewAdapter extends RecyclerView.Adapter<DirectionsCardViewAdapter.CardViewViewHolder> {

    protected List<RouteInstruction> titles;
    protected Context mContext;
    protected int layout;


    public DirectionsCardViewAdapter(Context context, int layout,List<RouteInstruction> titles) {
        mContext = context;
        this.layout = layout;
        this.titles = titles;
    }

    public void setTitles(List<RouteInstruction> titles) {
        this.titles = titles;
        notifyDataSetChanged();
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



        holder.tvTitle.setText(titles.get(position).getInstruction());
        holder.tvDistance.setText(titles.get(position).getDistance());
        holder.tvTime.setText(titles.get(position).getDuration());
        if(titles.get(position).getInstruction().startsWith("Turn left")){
            holder.arrowView.setImageResource(R.drawable.ic_arrow_left_black_24dp);
        }else if(titles.get(position).getInstruction().startsWith("Turn right")){
            holder.arrowView.setImageResource(R.drawable.ic_arrow_right_black_24dp);
        }else if(titles.get(position).getInstruction().startsWith("Slight left")){
            holder.arrowView.setImageResource(R.drawable.ic_arrow_top_left_black_24dp);
        }else if(titles.get(position).getInstruction().startsWith("Slight right")){
            holder.arrowView.setImageResource(R.drawable.ic_arrow_top_right_black_24dp);
        }else{
            holder.arrowView.setImageResource(R.drawable.ic_arrow_up_black_24dp);
        }


    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {


        public TextView tvTitle;
        public TextView tvDistance;
        public TextView tvTime;
        public CardView cardView;
        public ImageView arrowView;

        public CardViewViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.news_card_view);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            arrowView = (ImageView) itemView.findViewById(R.id.direction_iv);
        }
    }
}