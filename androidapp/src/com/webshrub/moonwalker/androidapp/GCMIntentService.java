/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webshrub.moonwalker.androidapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.google.android.gcm.GCMBaseIntentService;
import com.phonegap.plugins.pushnotifications.gcm.PushEventsTransmitter;
import com.phonegap.plugins.pushnotifications.gcm.PushManager;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
        super(PushManager.mSenderId);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        PushEventsTransmitter.onRegistered(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
    	PushEventsTransmitter.onUnregistered(context, registrationId);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        String message = getString(R.string.gcm_message);
        generateNotification(context, message);
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        String message = getString(R.string.gcm_deleted, total);
        generateNotification(context, message);
    }

    @Override
    public void onError(Context context, String errorId) {
    	PushEventsTransmitter.onRegisterError(context, errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
    	PushEventsTransmitter.onRegisterError(context, errorId);
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_stat_gcm;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, PhoneGapActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
        
        PushEventsTransmitter.onMessageReceive(context, message);
    }

}
