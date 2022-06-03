package com.example.wasla.Models.MapModel;

import java.io.Serializable;

public class Leg implements Serializable {

    private Distance distance;
    private Duration duration;
    private String end_address;
    private MapLocation end_location;
    private String start_address;
    private MapLocation start_location;

    public Leg() {
    }

    public Leg(Distance distance, Duration duration, String end_address, MapLocation end_location, String start_address, MapLocation start_location) {
        this.distance = distance;
        this.duration = duration;
        this.end_address = end_address;
        this.end_location = end_location;
        this.start_address = start_address;
        this.start_location = start_location;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public MapLocation getEnd_location() {
        return end_location;
    }

    public void setEnd_location(MapLocation end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public MapLocation getStart_location() {
        return start_location;
    }

    public void setStart_location(MapLocation start_location) {
        this.start_location = start_location;
    }
}
