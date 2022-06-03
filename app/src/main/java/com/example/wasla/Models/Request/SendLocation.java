package com.example.wasla.Models.Request;

public class SendLocation {

    public int clientId;
    public double longitud;
    public double latitud;

    public SendLocation(int clientId,DriverLocation location) {
        this.clientId = clientId;
        this.latitud= location.latitud;
        this.longitud = location.longitud;
    }
}

