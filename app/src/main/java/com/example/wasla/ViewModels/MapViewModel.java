package com.example.wasla.ViewModels;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wasla.ApiClint.ApiClint;
import com.example.wasla.ApiClint.MapApiClient;
import com.example.wasla.Models.MapModel.Route;
import com.example.wasla.Models.MapModel.RoutePointsResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapViewModel extends ViewModel {

    private final static String TAG = "MapViewModel";
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss a").create();
    private final Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();


    public final static String google_map_api_key = "AIzaSyB44dir8VgrRW0Bq5zu1qNdsmIlMIdD2Ak";

    public MutableLiveData<List<LatLng>> routePointResultLiveData;
    public MutableLiveData<RoutePointsResult> routeDistanceResultMutableLiveData;

    private static final MapApiClient mapApiClient = new MapApiClient();
    private static final ApiClint apiClient = new ApiClint();

    private static MapViewModel instance;

    public static MapViewModel getINSTANCE() {
        if (instance == null) {
            instance = new MapViewModel();
        }
        return instance;
    }


    public void resetMutableLiveData(LifecycleOwner lf) {
        routeDistanceResultMutableLiveData.removeObservers(lf);
        routePointResultLiveData.removeObservers(lf);
    }

    public void getRoadPoint(LatLng origin, LatLng destination) {

        List<LatLng> routeLine = new ArrayList<>();

        routePointResultLiveData = new MutableLiveData<>();
        routeDistanceResultMutableLiveData = new MutableLiveData<>();

        mapApiClient.getApIs().getRoutePoints("driving", "less_driving", convertLatLngToString(origin), convertLatLngToString(destination),
                google_map_api_key)
                .enqueue(new Callback<RoutePointsResult>() {
                    @Override
                    public void onResponse(@NotNull Call<RoutePointsResult> call, @NotNull Response<RoutePointsResult> response) {

                        Log.d(TAG, "onResponse: " + response.body());
                        if (response.isSuccessful()) {

                            assert response.body() != null;
                            List<Route> routes = response.body().getRoutes();
                            for (Route route : routes) {
                                String point = route.getOverviewPolyline().getPoints();
                                routeLine.addAll(decodePoints(point));
                            }
                            routeDistanceResultMutableLiveData.setValue(response.body());
                            routePointResultLiveData.setValue(routeLine);
                        } else {
                            routeDistanceResultMutableLiveData.setValue(null);
                            routePointResultLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<RoutePointsResult> call, @NotNull Throwable t) {
                        routeDistanceResultMutableLiveData.setValue(null);
                        routePointResultLiveData.setValue(null);
                    }
                });
    }

    private List<LatLng> decodePoints(String encodedPoints) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encodedPoints.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                if (index < encodedPoints.length()) {
                    b = encodedPoints.charAt(index++) - 63;
                } else break;
                result |= (b & 0x1f) << shift;
                shift += 5;
            }
            while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;


            shift = 0;
            result = 0;

            do {
                if (index < encodedPoints.length()) {
                    b = encodedPoints.charAt(index++) - 63;
                } else break;
                result |= (b & 0x1f) << shift;
                shift += 5;

            }
            while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng latLng = new LatLng(((double) lat / 1e5), ((double) lng / 1e5));
            poly.add(latLng);
        }
        return poly;
    }

    private String convertLatLngToString(LatLng latLng) {
        double lat = latLng.latitude;
        double lng = latLng.longitude;
        return lat + "," + lng;
    }

}
