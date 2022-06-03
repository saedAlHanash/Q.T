package com.example.wasla.AppConfig;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.wasla.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import static com.example.wasla.AppConfig.Cods.LOCATION_CHANGE;

/**
 * @deprecated  لم يعد يتم استخدامه من وقت الربط مع أثر
 */
public class SendLocationService extends Service {
    public SendLocationService() {
    }

    FusedLocationProviderClient mFusedClient;
    LocationRequest mLocationRequest;


    Intent intent = new Intent(LOCATION_CHANGE);


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        initLocation();

        return START_STICKY;
    }


    @SuppressLint("MissingPermission")
    public void initLocation() {

        this.mFusedClient = LocationServices.getFusedLocationProviderClient(this);

        sendLastLocation();

        mLocationRequest = LocationRequest.create()
                .setInterval(5000L)
                .setSmallestDisplacement(50.0F)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        mFusedClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());

    }


    /**
     * listener for location changes <br>
     * متنصت لتغيرات الموقع
     */
    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {

            intent.putExtra("lat", locationResult.getLastLocation().getLatitude());
            intent.putExtra("long", locationResult.getLastLocation().getLongitude());

            sendBroadcast(intent);

        }
    };

    @SuppressLint("MissingPermission")
    void sendLastLocation() {
        mFusedClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        Log.e("SAED_", "sendLastLocation: " );
                        intent.putExtra("lat", location.getLatitude());
                        intent.putExtra("long", location.getLongitude());

                        sendBroadcast(intent);
                    }
                });
    }


//    private void StartForeground() {
//
//        Intent intent = new Intent(getBaseContext(), MainMapActivity.class);
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0
//                , intent, PendingIntent.FLAG_ONE_SHOT);
//
//        String CHANNEL_ID = "channel_location";
//        String CHANNEL_NAME = "channel_location";
//
//        NotificationCompat.Builder builder = null;
//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//            notificationManager.createNotificationChannel(channel);
//            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
//            builder.setChannelId(CHANNEL_ID);
//            builder.setBadgeIconType(NotificationCompat.BADGE_ICON_NONE);
//        } else {
//            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
//        }
//
//        builder.setContentTitle("السائق متاح ");
//        builder.setContentText("يتم ارسال الموقع الحالي للسائق ");
//        builder.setAutoCancel(true);
//        builder.setSmallIcon(R.drawable.ic_logo);
//        builder.setContentIntent(pendingIntent);
//        Notification notification = builder.build();
//        startForeground(101, notification);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}