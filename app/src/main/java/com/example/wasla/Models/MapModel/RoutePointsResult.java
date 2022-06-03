package com.example.wasla.Models.MapModel;

import java.io.Serializable;
import java.util.List;

public class RoutePointsResult implements Serializable {

    private List<Route> routes;
    private String status;

    public RoutePointsResult() {
    }

    public RoutePointsResult(List<Route> routes, String status) {
        this.routes = routes;
        this.status = status;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
