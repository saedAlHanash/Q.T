package com.example.wasla.Models.MapModel;

import java.io.Serializable;

public class MapLocation implements Serializable {

    private double lat;
    private double lng;


    public MapLocation() {
    }

    public MapLocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "MapLocation{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
