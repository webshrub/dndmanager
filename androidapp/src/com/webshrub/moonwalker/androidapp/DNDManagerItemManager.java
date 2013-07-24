package com.webshrub.moonwalker.androidapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.ADDRESS_COLUMN;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.BODY_COLUMN;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.CACHED_NAME;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.DATE;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.DATE_COLUMN;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.INBOX_URI;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.NUMBER;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.SIMPLE_DATE_FORMAT;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.SIMPLE_DATE_TIME_FORMAT;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.UNKNOWN_COLUMN;

/**
 * Created by Ahsan.Javed.
 */
public class DNDManagerItemManager {
    private Context context;
    private String limiter;

    public DNDManagerItemManager(Context context) {
        this.context = context;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -3);
        Date limitDate = calendar.getTime();
        limiter = String.valueOf(limitDate.getTime());
    }

    public List<DNDManagerItem> getListing(DNDManagerItemType dndManagerItemType) {
        if (dndManagerItemType.equals(DNDManagerItemType.CALL)) {
            return getCallListing();
        } else {
            return getSMSListing();
        }
    }

    public JSONObject getListingAsJSON(DNDManagerItemType dndManagerItemType) throws JSONException {
        List<DNDManagerItem> dndManagerItemList;
        if (dndManagerItemType.equals(DNDManagerItemType.CALL)) {
            dndManagerItemList = getCallListing();
        } else {
            dndManagerItemList = getSMSListing();
        }
        JSONObject jsonObject = new JSONObject();
        if (dndManagerItemList.size() == 0) {
            jsonObject.put("rows", new JSONArray());
        } else {
            JSONObject row = new JSONObject();
            JSONArray rows = new JSONArray();
            for (DNDManagerItem dndManagerItem : dndManagerItemList) {
                row.put("date", dndManagerItem.getDate());
                row.put("datetime", dndManagerItem.getDateTime());
                row.put("number", dndManagerItem.getNumber());
                row.put("cachedName", dndManagerItem.getCachedName());
                row.put("text", dndManagerItem.getText());
                rows.put(row);
                row = new JSONObject();
            }
            jsonObject.put("rows", rows);
        }
        return jsonObject;
    }

    private List<DNDManagerItem> getCallListing() {
        List<DNDManagerItem> callLogList = new ArrayList<DNDManagerItem>();
        Uri contentUri = CallLog.Calls.CONTENT_URI;
        String[] projection = {CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,};
        String selection = CallLog.Calls.DATE + ">?" + "and " + CallLog.Calls.TYPE + "=" + CallLog.Calls.INCOMING_TYPE;
        String[] selectionArgs = new String[]{limiter};
        String sortOrder = null;
        Cursor cursor = context.getContentResolver().query(contentUri, projection, selection, selectionArgs, sortOrder);
        DNDManagerItem callLogItem = new DNDManagerItem();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(CACHED_NAME)) == null) {
                    callLogItem.setCachedName(UNKNOWN_COLUMN);
                } else if (DNDManagerHtmlHelper.getContactLogFlag(context)) {
                    callLogItem.setCachedName(cursor.getString(cursor.getColumnIndex(CACHED_NAME)));
                } else {
                    continue;
                }
                callLogItem.setItemType(DNDManagerItemType.CALL);
                callLogItem.setNumber(cursor.getString(cursor.getColumnIndex(NUMBER)));
                callLogItem.setDate(SIMPLE_DATE_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DATE)))));
                callLogItem.setDateTime(SIMPLE_DATE_TIME_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DATE)))));
                callLogItem.setText("<Please write calling company name here before sending>");
                callLogList.add(callLogItem);
                callLogItem = new DNDManagerItem();
            }
            cursor.close();
        }
        return callLogList;
    }

    private List<DNDManagerItem> getSMSListing() {
        List<DNDManagerItem> dndManagerItemList = new ArrayList<DNDManagerItem>();
        Uri inboxUri = Uri.parse(INBOX_URI);
        String[] projection = new String[]{ADDRESS_COLUMN, DATE_COLUMN, BODY_COLUMN};
        String selection = "date >?";
        String[] selectionArgs = new String[]{limiter};
        String sortOrder = null;
        Cursor cursor = context.getContentResolver().query(inboxUri, projection, selection, selectionArgs, sortOrder);
        DNDManagerItem dndManagerItem = new DNDManagerItem();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = DNDManagerUtil.getContactName(context, cursor.getString(cursor.getColumnIndex(ADDRESS_COLUMN)));
                if (name.equals("")) {
                    dndManagerItem.setCachedName(UNKNOWN_COLUMN);
                } else if (DNDManagerHtmlHelper.getContactLogFlag(context)) {
                    dndManagerItem.setCachedName(name);
                } else {
                    continue;
                }
                dndManagerItem.setItemType(DNDManagerItemType.SMS);
                dndManagerItem.setNumber(cursor.getString(cursor.getColumnIndex(ADDRESS_COLUMN)));
                dndManagerItem.setDate(SIMPLE_DATE_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DATE)))));
                dndManagerItem.setDateTime(SIMPLE_DATE_TIME_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DATE_COLUMN)))));
                dndManagerItem.setText(cursor.getString(cursor.getColumnIndex(BODY_COLUMN)));
                dndManagerItemList.add(dndManagerItem);
                dndManagerItem = new DNDManagerItem();
            }
            cursor.close();
        }
        return dndManagerItemList;
    }
}
