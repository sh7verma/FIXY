package com.app.fixy.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.app.fixy.R;
import com.app.fixy.activities.LandingActivity;
import com.app.fixy.interfaces.InterConst;
import com.app.fixy.utils.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by Shubham verma on 29-10-2018.
 */

public class FirebaseNotificationService extends FirebaseMessagingService {


    private static final String TAG = "Notification";
    Utils utils;

    @Override
    public void onCreate() {
        super.onCreate();
        utils = new Utils(getApplicationContext());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            Log.d(TAG, "Message data: " + remoteMessage.getData());
            sendNotification(data);
        }
        Log.d(TAG, "Message data: " + remoteMessage.getData());

    }

    private void sendNotification(Map<String, String> data) {

        Intent notificationIntent = null;
//        if (data.get("push_type").equals("1")) {
        if (utils.getBoolean(InterConst.FORGROUND, false)) {
            // if app open
            sendBroadcast(new Intent(InterConst.FRAG_MY_REQUEST_CLICK));
        } else {
            // app closed
            notificationIntent = new Intent(this, LandingActivity.class);
            generateNotification(data, 1, notificationIntent);// 1 for new request
        }
//        }

    }

    private void generateNotification(Map<String, String> messageBody, int notificationId, Intent notificationIntent) {
        int icon;
        long pattern[] = {100};
        String mess = messageBody.get("message");
        String title = messageBody.get("title");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            icon = R.mipmap.ic_splash_logo;
        } else {
            icon = R.mipmap.ic_splash_logo;
        }

        PendingIntent intent1 = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, String.valueOf(notificationId))
                .setSmallIcon(icon)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(mess)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mess))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.black))
                .setSound(defaultSoundUri)
                .setVibrate(pattern)
                .setContentIntent(intent1);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(String.valueOf(notificationId),
                    "NOTIFICATION_CHANNEL_1", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
            notificationChannel.enableVibration(true);
            notificationBuilder.setChannelId(String.valueOf(notificationId));
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(notificationId, notificationBuilder.build());

    }


    void silentNotification(Intent intent, String mess, int notificationId, String title) {
        int mIcon;
        long pattern[] = {1};
        long pattern_vibrate[] = {0, 100, 200, 300, 400};
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

            mIcon = R.drawable.ic_launcher_foreground;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mIcon = R.drawable.ic_launcher_foreground;
        else
            mIcon = R.drawable.ic_launcher_foreground;
        long pattern_noti[] = null;

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(mIcon)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(mess)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mess))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                .setVibrate(pattern_noti)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

}
