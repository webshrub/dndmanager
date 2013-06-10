package com.webshrub.moonwalker.androidapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by IntelliJ IDEA.
 * User: Zia khalid
 * Date: 4/9/13
 * Time: 10:14 AM
 */
public class DNDManagerSmsBroadCastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Intent notificationIntent = new Intent(context, DNDManagerDialogBox.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            int notificationId = 1;
            Notification notification = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.dnd_icon)
                    .setContentTitle("DND Manager")
                    .setContentText("You have spam calls and sms in your inbox. Report now?")
                    .setContentIntent(pendingIntent).build();
            notificationManager.notify(notificationId, notification);
        } else {
            Notification notification = new Notification(R.drawable.ic_launcher, "You have spam calls and sms in your inbox. Report now?", System.currentTimeMillis());
            Intent notificationIntent = new Intent(context, DNDManagerDialogBox.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            notification.setLatestEventInfo(context, "New Message!", "Report as spam", pendingIntent);
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notification.defaults = Notification.DEFAULT_SOUND;
            notificationManager.notify(1, notification);
        }
    }
}
