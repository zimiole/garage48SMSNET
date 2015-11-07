package com.staremisto.smsnet.data;

/**
 * Created by ozimokha on 07.11.2015.
 */
public class Weather {

    private String temperature;
    private String weatherCondition;
    private String date;
    private String time;

    public Weather(String temperature, String weatherCondition, String date, String time) {
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.date = date;
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
