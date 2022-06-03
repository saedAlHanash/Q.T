package com.example.wasla.Models.MapModel;

import java.io.Serializable;
import java.util.List;

public class Route implements Serializable {

    private OverviewPolyline overview_polyline;
    private List<Leg> legs;

    public Route() {
    }

    public Route(OverviewPolyline overview_polyline, List<Leg> legs) {
        this.overview_polyline = overview_polyline;
        this.legs = legs;
    }

    public Route(OverviewPolyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overview_polyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overview_polyline = overviewPolyline;
    }

    public OverviewPolyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(OverviewPolyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }
}
