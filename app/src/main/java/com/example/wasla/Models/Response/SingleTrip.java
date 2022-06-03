package com.example.wasla.Models.Response;

import android.content.Context;

import com.example.wasla.Models.Request.DriverLocation;
import com.example.wasla.R;
import com.google.android.gms.maps.model.ButtCap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class SingleTrip {

    PolylineOptions firstPoly = new PolylineOptions();

    public Trips.Result result;

    public SingleTrip(Trips.Result result) {
        this.result = result;
    }


    public SingleTrip() {
    }

    public void setFirsPoly(List<LatLng> list, Context context) {
        firstPoly.color(context.getResources().getColor(R.color.main_color));
        firstPoly.width(context.getResources().getDimension(R.dimen._2sdp));
        firstPoly.startCap(new ButtCap());
        for (int i = 0; i < list.size(); i++) {
            firstPoly.add(list.get(i));
        }

    }

    public LatLng getStartLL() {
        return new LatLng(this.result.currentLocation.latitud, this.result.currentLocation.longitud);
    }

    public LatLng getEndLL() {
        return new LatLng(this.result.destination.latitud, this.result.destination.longitud);
    }

    public DriverLocation getStartDL() {
        return this.result.currentLocation;
    }

    public DriverLocation getEndDL() {
        return this.result.destination;
    }

}
