package com.staremisto.smsnet.data;

/**
 * Created by orodr_000 on 07.11.2015.
 */
public class RouteInstruction {
    private String distance;
    private String duration;
    private String instruction;

    public RouteInstruction(String distance, String duration, String instruction) {
        this.distance = distance;
        this.duration = duration;
        this.instruction = instruction;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
