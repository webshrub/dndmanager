package com.webshrub.moonwalker.androidapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;

import java.util.HashMap;
import java.util.Map;

public class DNDManagerUtil {
    private static Map<String, String> contactMap = new HashMap<String, String>();

    public static String stripText(String input) {
        if (input.length() < 115) {
            return input;
        }
        return input.substring(0, 115);
    }

    public static String getMessageText(String number, String dateTime, String shortDescription) {
        String messageText = DNDManagerHtmlHelper.getMessageFormat().replaceAll("\\{number\\}", number);
        messageText = messageText.replaceAll("\\{dateTime\\}", dateTime);
        messageText = messageText.replaceAll("\\{shortDescription\\}", shortDescription);
        return messageText;
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
            if (contactMap.get(number) != null) {
                returnName = contactMap.get(number);
                return returnName;
            } else {
                Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
                Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        returnName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                        contactMap.put(number, returnName);
                    }
                }
            }
            if (returnName != null && returnName.equals("")) {
                contactMap.put(number, returnName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnName;
    }
}
