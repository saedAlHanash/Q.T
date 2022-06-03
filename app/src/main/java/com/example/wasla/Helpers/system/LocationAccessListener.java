package com.example.wasla.Helpers.system;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.LocationManager;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.example.wasla.AppConfig.Cods.DISTANCE_CHANGE;
import static com.example.wasla.AppConfig.Cods.REQUEST_CHECK_SETTINGS;
import static com.example.wasla.AppConfig.Cods.TIME_CHANGE;

public class LocationAccessListener extends BroadcastReceiver {


    private LocationRequest locationRequest;
    private Context context;
    private Task<LocationSettingsResponse> result;

    public LocationAccessListener(Context context) {
        this.context = context;
    }

    public LocationAccessListener Create() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(DISTANCE_CHANGE);
        locationRequest.setFastestInterval(TIME_CHANGE);
        return this;
    }

    public void Build() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        result = LocationServices.getSettingsClient(context)
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(onCompleteListener);
    }

    private final OnCompleteListener<LocationSettingsResponse> onCompleteListener = task -> {
        try {
            task.getResult(ApiException.class);
        } catch (ApiException e) {
            if (e.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                try {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    resolvableApiException.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {

        if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGpsEnabled || isNetworkEnabled) {

                //   Toast.makeText(context, "turnOn", Toast.LENGTH_SHORT).show();

            } else {
                // if GPS not Active
                Build();
            }
        }
    }
}

