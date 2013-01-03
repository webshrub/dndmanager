package com.webshrub.moonwalker.androidapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.provider.CallLog;
import android.telephony.SmsManager;
import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

public class SmsPlugin extends Plugin {
    public final String ACTION_SEND_SMS = "SendSMS";
    public final String CALL = "call";
    public final String SMS = "sms";
    public final String ON = "on";
    public final String OFF = "off";

    @Override
    public PluginResult execute(String action, JSONArray arg1, String callbackId) {
        PluginResult result = new PluginResult(Status.INVALID_ACTION);
        if (action.equals(ACTION_SEND_SMS)) {
            try {
                String phoneNumber = arg1.getString(0);
                String message = arg1.getString(1);
                sendSMS(phoneNumber, message);
                String isToDelete = arg1.getString(3);
                String reportType = arg1.getString(2);
                String number = arg1.getString(4);

                if (ON.equals(isToDelete)) {
                    if (CALL.equals(reportType)) {
                        deleteCallLogByNumber(number);
                    } else if (SMS.equals(reportType)) {
                        deleteSmsByNumber(number);
                    }
                }
                result = new PluginResult(Status.OK);
            } catch (JSONException ex) {
                result = new PluginResult(Status.JSON_EXCEPTION, ex.getMessage());
            }
        }
        return result;
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager manager = SmsManager.getDefault();
        PendingIntent sentIntent = PendingIntent.getActivity(this.ctx.getContext(), 0, new Intent(), 0);
        manager.sendTextMessage(phoneNumber, null, message, sentIntent, null);
    }

    public void deleteCallLogByNumber(String number) {
        try {
            String queryString = CallLog.Calls.NUMBER + " = '" + number + "'";
            ctx.getContext().getContentResolver().delete(CallLog.Calls.CONTENT_URI, queryString, null);
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    public void deleteSmsByNumber(String number) {
        try {
            String queryString = "address" + " = " + number;
            ctx.getContext().getContentResolver().delete(Uri.parse("content://sms"), queryString, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
