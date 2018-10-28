//package com.app.fixy.service;
//
//import android.content.Intent;
//import android.support.v4.content.LocalBroadcastManager;
//
//import com.app.fixy.interfaces.InterConst;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
///**
// * Created by Shubham verma on 29-10-2018.
// */
//
//public class MyFirebaseNotificationService extends FirebaseMessagingService {
//
//    private LocalBroadcastManager broadcaster;
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//
////        broadcaster = LocalBroadcastManager.getInstance(this);
//
//        Intent broadcastClickIntent = new Intent(InterConst.FRAG_MY_REQUEST_CLICK);
//        broadcaster.sendBroadcast(broadcastClickIntent);
//    }
//
////
////    void ringNotification(Intent intent, String mess, int notificationId, String title) {
////        int mIcon;
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
////            mIcon = R.mipmap.lollipop;
////        else
////            mIcon = R.mipmap.ic_launcher;
////
////        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, intent,
////                PendingIntent.FLAG_UPDATE_CURRENT);
////
////        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
////                .setSmallIcon(mIcon)
////                .setPriority(NotificationCompat.PRIORITY_MAX)
////                .setContentTitle(title)
////                .setContentText(mess)
////                .setStyle(new NotificationCompat.BigTextStyle().bigText(mess))
////                .setAutoCancel(true)
////                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSecondryGradient))
////                .setSound(defaultSoundUri)
////                .setContentIntent(pendingIntent);
////
////        NotificationManager notificationManager =
////                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////
////        notificationManager.notify(notificationId, notificationBuilder.build());
////
////        if (utils.getInt("Background", 0) == 1) {
////            int mBadgecount = utils.getInt("badge_count", 0);
////            mBadgecount++;
////            utils.setInt("badge_count", mBadgecount);
////            ShortcutBadger.applyCount(getApplicationContext(), utils.getInt("badge_count", 0));
////        }
////    }
////
////    void silentNotification(Intent intent, String mess, int notificationId, String title) {
////        int mIcon;
////        long pattern[] = {01};
////        long pattern_vibrate[] = {0, 100, 200, 300, 400};
////        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
////
////            mIcon = R.drawable._96;
////        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
////            mIcon = R.drawable._96;
////        else
////            mIcon = R.drawable._96;
////        long pattern_noti[] = null;
////        if (utils.getInt("vibration", 0) == 1)
////            pattern_noti = pattern_vibrate;
////        else
////            pattern_noti = pattern;
////
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
////                PendingIntent.FLAG_ONE_SHOT);
////
////        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
////                .setSmallIcon(mIcon)
////                .setPriority(NotificationCompat.PRIORITY_MAX)
////                .setContentTitle(title)
////                .setContentText(mess)
////                .setStyle(new NotificationCompat.BigTextStyle().bigText(mess))
////                .setAutoCancel(true)
////                .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
////                .setVibrate(pattern_noti)
////                .setContentIntent(pendingIntent);
////
////        NotificationManager notificationManager =
////                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////
////        notificationManager.notify(notificationId, notificationBuilder.build());
////
////
////    }
//
//}
