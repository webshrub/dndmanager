package com.webshrub.moonwalker.androidapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.SIMPLE_DATE_FORMAT;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.SIMPLE_DATE_TIME_FORMAT;

public class DNDManagerUtil {

    public static void toastMessage(Activity activity, String toastMessage) {
        Toast toast = new Toast(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View toastRoot = inflater.inflate(R.layout.toast, null);
        toast.setView(toastRoot);
        TextView textView = (TextView) toastRoot.findViewById(R.id.toastMessage);
        textView.setText(toastMessage);
        textView.setTextColor(Color.BLACK);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static String stripText(String input) {
        if (input.length() < 115) {
            return input;
        }
        return input.substring(0, 115);
    }

    public static String getMessageText(Context context, String number, String dateTime, String shortDescription) {
        try {
            String messageText = DNDManagerHtmlHelper.getMessageFormat(context).replaceAll("~~number~~", number);
            messageText = messageText.replaceAll("~~date~~", SIMPLE_DATE_FORMAT.format(SIMPLE_DATE_TIME_FORMAT.parse(dateTime)));
            messageText = messageText.replaceAll("~~datetime~~", dateTime);
            messageText = messageText.replaceAll("~~text~~", shortDescription);
            return messageText;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getIncomingNumberFromSms(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            return messages[0].getOriginatingAddress();
        }
        return "";
    }

    public static String getContactName(Context context, String number) {
        String returnName = "";
        try {
            String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup.NUMBER};
            String selection = ContactsContract.PhoneLookup.NUMBER + "=?";
            String[] selectionArgs = new String[]{number};
            String sortOrder = null;
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    returnName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnName;
    }

    public static void sendSMS(Context context, String phoneNumber, String message) {
        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(message)) {
            toastMessage((Activity) context, "Either phone number is blank or message is blank. Please try again.");
        } else {
            SmsManager manager = SmsManager.getDefault();
            PendingIntent sentIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
            manager.sendTextMessage(phoneNumber, null, stripText(message), sentIntent, null);
        }
    }

    public static void deleteCallLogByNumber(Context context, String number) {
        try {
            String queryString = CallLog.Calls.NUMBER + " = ? ";
            context.getContentResolver().delete(CallLog.Calls.CONTENT_URI, queryString, new String[]{number});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteSmsByNumber(Context context, String number) {
        try {
            String queryString = "address" + " = ? ";
            context.getContentResolver().delete(Uri.parse("content://sms"), queryString, new String[]{number});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSentSms(Context context, String phoneNumber, String message) {
        ContentValues values = new ContentValues();
        values.put("address", phoneNumber);
        values.put("date", System.currentTimeMillis());
        values.put("read", 1);
        values.put("status", -1);
        values.put("type", 2);
        values.put("body", message);
        Uri inserted = context.getContentResolver().insert(Uri.parse("content://sms"), values);
    }
}
