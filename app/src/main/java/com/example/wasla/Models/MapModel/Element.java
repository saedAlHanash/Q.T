package com.example.wasla.Models.MapModel;

import java.io.Serializable;

public class Element implements Serializable {

   private Distance distance;
   private Duration duration;
   private String status;

    public Element() {
    }


    public Element(Distance distance, Duration duration, String status) {
        this.distance = distance;
        this.duration = duration;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
