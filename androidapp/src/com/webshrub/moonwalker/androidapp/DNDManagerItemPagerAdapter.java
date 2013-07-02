package com.webshrub.moonwalker.androidapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.CallLog;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
        DNDManagerItem dndManagerItem = dndManagerItems.get(position);
        View layout = ((Activity) context).getLayoutInflater().inflate(R.layout.message_item_details, view, false);
        TextView from = (TextView) layout.findViewById(R.id.from);
        from.setText(dndManagerItem.getCachedName() + " (" + dndManagerItem.getNumber() + ")");
        EditText shortDescription = (EditText) layout.findViewById(R.id.shortDescription);
        shortDescription.setText(dndManagerItem.getText());
        EditText messageText = (EditText) layout.findViewById(R.id.messageText);
        messageText.setText(DNDManagerUtil.getMessageText(context, dndManagerItem.getNumber(), dndManagerItem.getDateTime(), dndManagerItem.getText()));
        messageText.setTag(dndManagerItem.getDateTime());
        shortDescription.addTextChangedListener(new DNDManagerMessageTextWatcher(context, dndManagerItem, shortDescription, messageText));
        if (dndManagerItem.getItemType().equals(DNDManagerItemType.CALL)) {
            shortDescription.setSelection(dndManagerItem.getText().length());
            shortDescription.extendSelection(0);
            shortDescription.setOnClickListener(new DNDManagerDescriptionOnClickListener(shortDescription));
        }
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
        String[] projection = new String[]{DNDManagerConstants.ADDRESS_COLUMN, DNDManagerConstants.DATE_COLUMN, DNDManagerConstants.BODY_COLUMN};
        String selection = "date >?";
        String[] selectionArgs = new String[]{limiter};
        String sortOrder = null;
        Cursor cursor = context.getContentResolver().query(inboxUri, projection, selection, selectionArgs, sortOrder);
        DNDManagerItem dndManagerItem = new DNDManagerItem();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = DNDManagerUtil.getContactName(context, cursor.getString(cursor.getColumnIndex(DNDManagerConstants.ADDRESS_COLUMN)));
                if (name.equals("")) {
                    dndManagerItem.setCachedName(DNDManagerConstants.UNKNOWN_COLUMN);
                } else if (contactLogFlag) {
                    dndManagerItem.setCachedName(name);
                } else {
                    continue;
                }
                dndManagerItem.setItemType(DNDManagerItemType.SMS);
                dndManagerItem.setNumber(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.ADDRESS_COLUMN)));
                dndManagerItem.setDateTime(SIMPLE_DATE_TIME_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DNDManagerConstants.DATE_COLUMN)))));
                dndManagerItem.setText(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.BODY_COLUMN)));
                dndManagerItemList.add(dndManagerItem);
                dndManagerItem = new DNDManagerItem();
            }
            cursor.close();
        }
        return dndManagerItemList;
    }

    private List<DNDManagerItem> getCallListing(String limiter, boolean contactLogFlag) {
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
                if (cursor.getString(cursor.getColumnIndex(DNDManagerConstants.CACHED_NAME)) == null) {
                    callLogItem.setCachedName(DNDManagerConstants.UNKNOWN_COLUMN);
                } else if (contactLogFlag) {
                    callLogItem.setCachedName(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.CACHED_NAME)));
                } else {
                    continue;
                }
                callLogItem.setItemType(DNDManagerItemType.CALL);
                callLogItem.setNumber(cursor.getString(cursor.getColumnIndex(DNDManagerConstants.NUMBER)));
                callLogItem.setDateTime(SIMPLE_DATE_TIME_FORMAT.format(new Date(cursor.getLong(cursor.getColumnIndex(DNDManagerConstants.DATE)))));
                callLogItem.setText("<Please write calling company name here before sending>");
                callLogList.add(callLogItem);
                callLogItem = new DNDManagerItem();
            }
            cursor.close();
        }
        return callLogList;
    }

    public DNDManagerItem removeDNDManagerItem(int position) {
        return dndManagerItems.remove(position);
    }

    public DNDManagerItem getDNDManagerItem(int position) {
        return dndManagerItems.get(position);
    }

    private static class DNDManagerMessageTextWatcher implements TextWatcher {
        private Context context;
        private DNDManagerItem dndManagerItem;
        private EditText shortDescription;
        private EditText messageText;

        public DNDManagerMessageTextWatcher(Context context, DNDManagerItem dndManagerItem, EditText shortDescription, EditText messageText) {
            this.context = context;
            this.dndManagerItem = dndManagerItem;
            this.shortDescription = shortDescription;
            this.messageText = messageText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String message = DNDManagerUtil.stripText(shortDescription.getText().toString());
            message = DNDManagerUtil.getMessageText(context, dndManagerItem.getNumber(), dndManagerItem.getDateTime(), message);
            messageText.setText(message);
        }
    }

    private static class DNDManagerDescriptionOnClickListener implements View.OnClickListener {
        private EditText shortDescription;
        private int textEraseCount;

        public DNDManagerDescriptionOnClickListener(EditText shortDescription) {
            this.shortDescription = shortDescription;
            this.textEraseCount = 0;
        }

        @Override
        public void onClick(View view) {
            if (textEraseCount == 0) {
                shortDescription.setText("");
                textEraseCount = 1;
            }
        }
    }
}
