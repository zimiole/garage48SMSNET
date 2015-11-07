package com.staremisto.smsnet.data;

/**
 * Created by ozimokha on 07.11.2015.
 */
public class FourSquare {

    private String name;
    private String address;
    private String city;
    private String checkIns;

    public FourSquare(String name, String address, String city, String checkIns) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.checkIns = checkIns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(String checkIns) {
        this.checkIns = checkIns;
    }
}
