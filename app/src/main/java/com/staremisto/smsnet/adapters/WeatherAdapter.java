package com.staremisto.smsnet.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.staremisto.smsnet.R;
import com.staremisto.smsnet.data.Weather;
import com.staremisto.smsnet.utils.Utility;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    // Flag to determine if we want to use a separate view for "today".
    private boolean mUseTodayLayout = true;

    private List<Weather> list;

    public WeatherAdapter(List<Weather> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutId = -1;
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                layoutId = R.layout.weather_item_header;
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                layoutId = R.layout.weather_item;
                break;
            }
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        switch (position) {
            case 0: {
                // Get weather icon
                if (!TextUtils.isEmpty(list.get(position).getWeatherCondition())) {
                    viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(list.get(position).getWeatherCondition()));
                }
                break;
            }
            default: {
                // Get weather icon
                if (!TextUtils.isEmpty(list.get(position).getWeatherCondition())) {
                    viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(list.get(position).getWeatherCondition()));
                }
                break;
            }
        }

        viewHolder.dateView.setText("Today at ".concat(list.get(position).getTime()));

        if (!TextUtils.isEmpty(list.get(position).getWeatherCondition())) {
            viewHolder.descriptionView.setText(list.get(position).getWeatherCondition());
        }

        if (!TextUtils.isEmpty(list.get(position).getTemperature())) {
            viewHolder.highTempView.setText(list.get(position).getTemperature().concat("°C"));
        }

//        // Read date from cursor
//        String dateString = list.get(position).getDate();
//        // Find TextView and set formatted date on it
//        viewHolder.dateView.setText(Utility.getFriendlyDayString(context, dateString));
//
//        // Read weather forecast from cursor
//        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
//        // Find TextView and set weather forecast on it
//        viewHolder.descriptionView.setText(description);
//
//        // For accessibility, add a content description to the icon field
//        viewHolder.iconView.setContentDescription(description);
//
//        // Read user preference for metric or imperial temperature units
//        boolean isMetric = Utility.isMetric(context);
//
//        // Read high temperature from cursor
//        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
//        viewHolder.highTempView.setText(Utility.formatTemperature(context, high));
//
//        // Read low temperature from cursor
//        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
//        viewHolder.lowTempView.setText(Utility.formatTemperature(context, low));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setWeatherList(List<Weather> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView highTempView;
        public final TextView lowTempView;

        public ViewHolder(View view) {
            super(view);
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }
}
