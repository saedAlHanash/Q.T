package com.example.wasla.Helpers.system;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import static com.example.wasla.AppConfig.Cods.REQUEST_CODE_CALL_PHONE;
import static com.example.wasla.AppConfig.Cods.REQUEST_CODE_GPS;
import static com.example.wasla.AppConfig.Cods.REQUEST_CODE_READ_EXTERNAL_STORAGE;


public class GetPermissions {


    /**
     * Request access permissions to call the phone
     *
     * @param activity The current activity
     * @return if get {true} permission gated successfully
     */
    public static boolean getCallPhonePermission(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                activity.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL_PHONE);
            } else {
                return true;
            }
        }

        return false;
    }


    /**
     * Request access permissions to READ EXTERNAL STORAGE
     *
     * @param activity The current activity
     * @return if get {true} permission gated successfully
     */
    public static boolean checkPermeationREAD_EXTERNAL_STORAGE(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
                activity.requestPermissions(new String[]{permission}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
            } else
                return true;
        }
        return false;

    }


    public static boolean checkPermeationLocation(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_GPS);
        } else {
            return true;
        }
        return false;
    }
}

