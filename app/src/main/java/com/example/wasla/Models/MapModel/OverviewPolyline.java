package com.example.wasla.Models.MapModel;

import java.io.Serializable;

public class OverviewPolyline implements Serializable {

    private   String points;


    public OverviewPolyline() {
    }

    public OverviewPolyline(String points) {
        this.points = points;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
