package com.example.wasla.Models.Request;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class DriverLocation {

    public double latitud;

    public double longitud;

    public DriverLocation() {
    }

    public DriverLocation(double latitud, double longitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public DriverLocation(Location location) {
        if (location != null) {
            this.longitud = location.getLongitude();
            this.latitud = location.getLatitude();
        }
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public LatLng getLatLing() {
        return new LatLng(latitud, longitud);
    }

    public String toString() {
        return this.latitud +
                "," +
                this.longitud;
    }

}
