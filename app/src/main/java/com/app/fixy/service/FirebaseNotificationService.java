package com.app.fixy.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.app.fixy.R;
import com.app.fixy.interfaces.InterConst;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Shubham verma on 29-10-2018.
 */

public class FirebaseNotificationService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(this);

        Intent broadcastClickIntent = new Intent(InterConst.FRAG_MY_REQUEST_CLICK);
        broadcaster.sendBroadcast(broadcastClickIntent);
    }


    void ringNotification(Intent intent, String mess, int notificationId, String title) {
        int mIcon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mIcon = R.mipmap.ic_launcher;
        else
            mIcon = R.mipmap.ic_launcher;

        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(mIcon)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(mess)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mess))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent))
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());


    }

    void silentNotification(Intent intent, String mess, int notificationId, String title) {
        int mIcon;
        long pattern[] = {01};
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
