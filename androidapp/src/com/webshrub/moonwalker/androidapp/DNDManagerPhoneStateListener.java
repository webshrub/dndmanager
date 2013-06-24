package com.webshrub.moonwalker.androidapp;

/**
 * Created by IntelliJ IDEA.
 * User: Zia khalid
 * Date: 4/9/13
 * Time: 1:34 PM
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class DNDManagerPhoneStateListener extends PhoneStateListener {
    private Context context;

    public DNDManagerPhoneStateListener(Context context) {
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
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
                    Notification notification = new Notification(R.drawable.dnd_icon, "You have spam calls and sms in your inbox. Report now?", System.currentTimeMillis());
                    Intent notificationIntent = new Intent(context, DNDManagerDialogBox.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
                    notification.setLatestEventInfo(context, "DND Manager", "You have spam calls and sms in your inbox. Report now?", pendingIntent);
                    notificationManager.notify(1, notification);
                }
                break;
        }
    }
}
