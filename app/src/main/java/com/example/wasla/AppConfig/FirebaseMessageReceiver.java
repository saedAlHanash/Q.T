package com.example.wasla.AppConfig;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.wasla.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static com.example.wasla.AppConfig.Cods.*;
import static com.example.wasla.AppConfig.Cods.CANCEL_TRIP;
import static com.example.wasla.AppConfig.Cods.DEACTIVATE;
import static com.example.wasla.AppConfig.Cods.HAVE_TRIP;
import static com.example.wasla.AppConfig.SharedPreference.CACHE_FIRE_TOKEN;
import static com.example.wasla.AppConfig.SharedPreference.GET_ACCESS_TOKEN;
import static com.example.wasla.AppConfig.SharedPreference.GET_FIRE_TOKEN;


public class FirebaseMessageReceiver extends FirebaseMessagingService {

    private final static String TAG = "SAED_Firebase";



/*    @Override
    public void handleIntent(@NonNull Intent intent) {


        if (!isAppForground(getBaseContext())) {
            if (!intent.getExtras().getString("gcm.notification.title").equals("DriverLocation"))
                super.handleIntent(intent);
        } else {
            super.handleIntent(intent);
        }
        Bundle ss = intent.getExtras();
        Log.d(TAG, "handleIntent: " + ss.getString("gcm.notification.body"));
        Log.d(TAG, "handleIntent: " + ss.getString("gcm.notification.title"));
    }*/

    /**
     * to know if app in foreground or in background
     *
     * @param mContext app context
     * @return if app in foreground return true
     */
    public boolean isAppForeground(Context mContext) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            return topActivity.getPackageName().equals(mContext.getPackageName());
        }
        return true;
    }

    @Override
    public void
    onMessageReceived(@NotNull RemoteMessage remoteMessage) {

        /*
        سيناريو العمل : الفلترة حسب ال title لمعرفة نوع الاشعار
        ثم تهيئة ال Intent  ثم عمل broadcast
         */

        Log.d(TAG, "onMessageReceived: " + remoteMessage.getData().toString());

//        if (remoteMessage.getData().get("title") != null) {
//            switch (Objects.requireNonNull(remoteMessage.getData().get("title"))) {
//                case HAVE_TRIP: {
//                    //وصول رحلة جديدة
//                    SharedPreference.getInstance(this);
//                    //اذا كان التطبيق مفتوح
//                    if (isAppForeground(this)) {
//                        Intent intent = new Intent();
//
//                        intent.setAction(HAVE_TRIP);
//                        intent.putExtra(HAVE_TRIP, remoteMessage.getData().get("body"));
//
//                        //ارسال الاشارة لل activity
//                        sendBroadcast(intent);
//
//                    } else {
//
//                        //اذا التطبيق بالباك
//                        try {
//                            //استخراج ال trip id  من الاشعار
//                            String numberOnly = Objects.requireNonNull(remoteMessage.getData().get("body")).replaceAll("[^0-9]", "");
//                            //تحويل لرقم
//                            int id = Integer.parseInt(numberOnly);
//                            //تخزينه في الملف
//                            SharedPreference.CASH_TRIP(id);
//                            //اظهار اشعار
//                            showNotification("تلقيت طلب جديد", "المس هنا أو قم بفتح التطبيق للتحقق من الرحلة ");
//                        } catch (Exception ignore) {
//                        }
//                    }
//                    break;
//
//                }
//
//                case CANCEL_TRIP: {
//
//                    /*الرحلة ألغيت */
//                    Intent intent = new Intent();
//                    SharedPreference.getInstance(this);
//
////                    اذا الرحلة كانت مخزنة بالملق
//                    if (SharedPreference.IS_THERE_TRIP())
//
//                        //ارسال اشعار بالالغاء
//                        showNotification("الغاء الرحلة ", "الرحلة ألغيت");
//
//                    //مسح الرحلة من الملف
//                    SharedPreference.REMOVE_CASH_TRIP();
//
//                    Log.e(TAG, "CancelTrip : ");
//
//                    intent.setAction(CANCEL_TRIP);
//
//                    if (isAppForeground(this))
//                        sendBroadcast(intent);
//
//                    break;
//                }
//
//                case DEACTIVATE: {
//
//                    SharedPreference.getInstance(this);
//
//                    SharedPreference.REMOVE_TOKEN();
//
//                    showNotificationWithoutIntent("تم الغاء تفعيل حسابك ", "يرجى مراسلة الادارة ");
//
//                    Intent intent = new Intent();
//                    intent.setAction(DEACTIVATE);
//
//                    if (isAppForeground(this))
//                        sendBroadcast(intent);
//
//                    break;
//
//                }
//            }
//        }

    }

//    /**
//     * تصميم للاشعار
//     */
//    private RemoteViews getCustomDesign(String title, String message) {
//        RemoteViews remoteViews = new RemoteViews(
//                getApplicationContext().getPackageName(),
//                R.layout.notification);
//
//        remoteViews.setTextViewText(R.id.title, title);
//        remoteViews.setTextViewText(R.id.message, message);
//        remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_up_logo);
//
//        return remoteViews;
//    }
//
//
//    public void showNotification(String title, String message) {
//
//        Intent intent = new Intent(this, MainMapActivity.class);
//        String channel_id = "notification_channel";
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder builder = new NotificationCompat
//                .Builder(getApplicationContext(),
//                channel_id)
//                .setSmallIcon(R.drawable.ic_question_circle_fill)
//                .setAutoCancel(true)
//                .setVibrate(new long[]{1000, 1000})
//                .setOnlyAlertOnce(true)
//                .setSmallIcon(R.drawable.ic_light_logo)
//                .setContentIntent(pendingIntent);
//
//        builder = builder.setContent(getCustomDesign(title, message));
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel(
//                    channel_id, "web_app",
//                    NotificationManager.IMPORTANCE_HIGH);
//
//            notificationManager.createNotificationChannel(
//                    notificationChannel);
//        }
//
//        notificationManager.notify(0, builder.build());
//    }
//
//    public void showNotificationWithoutIntent(String title, String message) {
//
//        Intent intent = new Intent(this, MainMapActivity.class);
//        String channel_id = "4652";
//
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        NotificationCompat.Builder builder = new NotificationCompat
//                .Builder(getApplicationContext(),
//                channel_id)
//                .setSmallIcon(R.drawable.ic_question_circle_fill)
//                .setAutoCancel(true)
//                .setVibrate(new long[]{1000, 1000})
//                .setOnlyAlertOnce(true)
//                .setSmallIcon(R.drawable.ic_light_logo);
//
//        builder = builder.setContent(getCustomDesign(title, message));
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel notificationChannel = new NotificationChannel(
//                    channel_id, "web_app",
//                    NotificationManager.IMPORTANCE_HIGH);
//
//            notificationManager.createNotificationChannel(
//                    notificationChannel);
//        }
//
//        notificationManager.notify(0, builder.build());
//    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        // يتم تكييش نجاح الارسال في ال view model
        Log.d(TAG, "onNewToken: " + s);

        SharedPreference.getInstance(getBaseContext());

        CACHE_FIRE_TOKEN(s);

//        // عند وجود توكين ارسل توكين الفاير بيز
//        if (!GET_ACCESS_TOKEN().isEmpty())
//            UserViewModel.getInstance().postFirToken(GET_FIRE_TOKEN());

    }


    public interface callBackFireBase {
        void message(String title, String body);
    }


}


