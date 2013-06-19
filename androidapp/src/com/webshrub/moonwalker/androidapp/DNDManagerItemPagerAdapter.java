package com.webshrub.moonwalker.androidapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.*;

import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.SIMPLE_DATE_FORMAT;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.SIMPLE_DATE_TIME_FORMAT;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 3/20/13
 * Time: 9:50 PM
 */
class DNDManagerItemPagerAdapter extends PagerAdapter {
    private Context context;
    private List<DNDManagerItem> dndManagerItems;
    private Map<String, String> contactMap = new HashMap<String, String>();

    public DNDManagerItemPagerAdapter(Context context, boolean contactLogFlag) {
        this.context = context;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -3);
        Date limitDate = calendar.getTime();
        String limiter = String.valueOf(limitDate.getTime());
        dndManagerItems = getCallListing(limiter, contactLogFlag);
        dndManagerItems.addAll(getSMSListing(limiter, contactLogFlag));
        if (dndManagerItems.size() > 0) {
            Collections.sort(dndManagerItems);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(View container) {
    }

    @Override
    public int getCount() {
        return dndManagerItems.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View layout = ((Activity) context).getLayoutInflater().inflate(R.layout.message_item_details, view, false);
        EditText messageTextView = (EditText) layout.findViewById(R.id.messageText);
        messageTextView.setText(dndManagerItems.get(position).getText());
        view.addView(layout, 0);
        return layout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View container) {
    }

    private List<DNDManagerItem> getSMSListing(String limiter, boolean contactLogFlag) {
        List<DNDManagerItem> dndManagerItemList = new ArrayList<DNDManagerItem>();
        Uri inboxUri = Uri.parse(DNDManagerConstants.INBOX_URI);
        String[] projection = new String[]{DNDManagerConstants.ID_COLUMN, DNDManagerConstants.ADDRESS_COLUMN, DNDManagerConstants.DATE_COLUMN, DNDManagerConstants.BODY_COLUMN};
        String selection = "date >?";
        String[] selectionArgs = new String[]{limiter};
        String sortOrder = null;
        Cursor cursor = context.getContentResolver().query(inboxUri, projection, selection, selectionArgs, sortOrder);
        DNDManagerItem dndManagerItem = new DNDManagerItem();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = getContact(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.ADDRESS_COLUMN)));
                if (name.equals("")) {
                    dndManagerItem.setItemType(DNDManagerItemType.SMS);
                    dndManagerItem.setId(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.ID_COLUMN)));
                    dndManagerItem.setNumber(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.ADDRESS_COLUMN)));
                    dndManagerItem.setCachedName(DNDManagerConstants.UNKNOWN_COLUMN);
                    dndManagerItem.setDate(SIMPLE_DATE_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DNDManagerConstants.DATE_COLUMN)))));
                    dndManagerItem.setDateTime(SIMPLE_DATE_TIME_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DNDManagerConstants.DATE_COLUMN)))));
                    dndManagerItem.setText(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.BODY_COLUMN)));
                    dndManagerItemList.add(dndManagerItem);
                    dndManagerItem = new DNDManagerItem();
                } else if (contactLogFlag) {
                    dndManagerItem.setItemType(DNDManagerItemType.SMS);
                    dndManagerItem.setId(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.ID_COLUMN)));
                    dndManagerItem.setNumber(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.ADDRESS_COLUMN)));
                    dndManagerItem.setCachedName(name);
                    dndManagerItem.setDate(SIMPLE_DATE_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DNDManagerConstants.DATE_COLUMN)))));
                    dndManagerItem.setDateTime(SIMPLE_DATE_TIME_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DNDManagerConstants.DATE_COLUMN)))));
                    dndManagerItem.setText(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.BODY_COLUMN)));
                    dndManagerItemList.add(dndManagerItem);
                    dndManagerItem = new DNDManagerItem();
                }
            }
        }
        return dndManagerItemList;
    }

    private String getContact(String number) {
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

    private List<DNDManagerItem> getCallListing(String limiter, boolean contactLogFlag) {
        List<DNDManagerItem> callLogList = new ArrayList<DNDManagerItem>();
        String[] strFields = {
                CallLog.Calls.DATE,
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DURATION,
                CallLog.Calls.NEW,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.CACHED_NUMBER_TYPE,
        };
        try {
            Cursor cursor = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    strFields,
                    CallLog.Calls.DATE + ">?" + "and " + CallLog.Calls.TYPE + "=" + CallLog.Calls.INCOMING_TYPE,
                    new String[]{limiter},
                    CallLog.Calls.DEFAULT_SORT_ORDER);
            if (cursor != null) {
                int callCount = cursor.getCount();
                if (callCount > 0) {
                    DNDManagerItem callLogItem = new DNDManagerItem();
                    cursor.moveToFirst();
                    do {
                        if (cursor.getString(5) == null) {
                            callLogItem.setItemType(DNDManagerItemType.CALL);
                            callLogItem.setDate(SIMPLE_DATE_FORMAT.format(new Date(cursor.getLong(0))));
                            callLogItem.setDateTime(SIMPLE_DATE_TIME_FORMAT.format(new Date(cursor.getLong(0))));
                            callLogItem.setNumber(cursor.getString(1));
                            callLogItem.setType(cursor.getInt(2));
                            callLogItem.setDuration(cursor.getLong(3));
                            callLogItem.setNewFlag(cursor.getInt(4));
                            callLogItem.setCachedName(DNDManagerConstants.UNKNOWN_COLUMN);
                            callLogItem.setText(" <Please write calling company name here before sending>");
                            callLogItem.setCachedNumberType(cursor.getInt(6));
                            callLogList.add(callLogItem);
                            callLogItem = new DNDManagerItem();
                        } else if (contactLogFlag) {
                            callLogItem.setItemType(DNDManagerItemType.CALL);
                            callLogItem.setDate(SIMPLE_DATE_FORMAT.format(new Date(cursor.getLong(0))));
                            callLogItem.setDate(SIMPLE_DATE_TIME_FORMAT.format(new Date(cursor.getLong(0))));
                            callLogItem.setNumber(cursor.getString(1));
                            callLogItem.setType(cursor.getInt(2));
                            callLogItem.setDuration(cursor.getLong(3));
                            callLogItem.setNewFlag(cursor.getInt(4));
                            callLogItem.setCachedName(DNDManagerConstants.UNKNOWN_COLUMN);
                            callLogItem.setText(" <Please write calling company name here before sending>");
                            callLogItem.setCachedNumberType(cursor.getInt(6));
                            callLogList.add(callLogItem);
                            callLogItem = new DNDManagerItem();
                        }
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.d("CallLog_Plugin", " ERROR : SQL to get cursor: ERROR " + e.getMessage());
        }
        return callLogList;
    }

    public DNDManagerItem removeDNDManagerItem(int position) {
        return dndManagerItems.remove(position);
    }

    public DNDManagerItem getDNDManagerItem(int position) {
        return dndManagerItems.get(position);
    }
}
