package com.staremisto.smsnet.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.staremisto.smsnet.R;
import com.staremisto.smsnet.data.FourSquare;

import java.util.List;

/**
 * Created by ozimokha on 07.11.2015.
 */
public class FoursquareAdapter extends RecyclerView.Adapter<FoursquareAdapter.CardViewViewHolder> {

    protected List<FourSquare> list;
    protected Context mContext;
    protected int layout;

    public FoursquareAdapter(Context context, int layout, List<FourSquare> list) {
        mContext = context;
        this.layout = layout;
        this.list = list;
    }

    public void setList(List<FourSquare> list) {
        this.list = list;
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
        holder.tvTitle.setText(list.get(position).getName());
        holder.tvCity.setText(holder.tvCity.getText().toString().concat(" ").concat(list.get(position).getCity()));
        holder.tvAddress.setText(holder.tvAddress.getText().toString().concat(" ").concat(list.get(position).getAddress()));
        holder.tvCheckIns.setText(holder.tvCheckIns.getText().toString().concat(" ").concat(list.get(position).getCheckIns()));
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvCity;
        public TextView tvAddress;
        public TextView tvCheckIns;
        public CardView cardView;

        public CardViewViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.news_card_view);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvCity = (TextView) itemView.findViewById(R.id.tv_city);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvCheckIns = (TextView) itemView.findViewById(R.id.tv_check_ins);
        }
    }
}
