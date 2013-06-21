package com.webshrub.moonwalker.androidapp;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.*;

public class DNDManagerDialogBox extends FragmentActivity {
    private ViewPager viewPager;
    private DNDManagerItemPagerAdapter pagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogbox);
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new DNDManagerItemPagerAdapter(this, DNDManagerHtmlHelper.getContactLogFlag());
        viewPager.setAdapter(pagerAdapter);
        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new CancelButtonOnClickListener());
        Button reportSpamButton = (Button) findViewById(R.id.reportSpam);
        reportSpamButton.setOnClickListener(new ReportSpamButtonOnClickListener());
        if (pagerAdapter.getCount() == 0) {
            reportSpamButton.setEnabled(false);
            Toast.makeText(DNDManagerDialogBox.this, "Hurray! No spam calls/sms in your inbox.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(DNDManagerDialogBox.this, "Showing only last 3 day's calls and sms as per TRAI guidelines.", Toast.LENGTH_LONG).show();
        }
    }

    private class ReportSpamButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            DNDManagerItem dndManagerItem = pagerAdapter.getDNDManagerItem(viewPager.getCurrentItem());
            String dateTime = dndManagerItem.getDateTime();
            EditText editText = (EditText) viewPager.findViewWithTag(dateTime);
            String messageText = editText.getText().toString().trim();
            if (messageText.equals("")) {
                Toast toast = Toast.makeText(DNDManagerDialogBox.this, "Please type short description of the call/spam your received.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                sendSMS(TRAI_CONTACT_NUMBER, messageText);
                if (!DNDManagerHtmlHelper.getDeleteSentSMSFlag()) {
                    saveSentSms(TRAI_CONTACT_NUMBER, messageText);
                }
                if (DNDManagerHtmlHelper.getDeleteDNDManagerItemFlag()) {
                    deleteDNDManagerItem(viewPager.getCurrentItem());
                }
                Toast toast = Toast.makeText(DNDManagerDialogBox.this, "Your request has been submitted successfully.", Toast.LENGTH_LONG);
                toast.show();
            }
        }

        private void sendSMS(String number, String message) {
            ArrayList<String> messages = SmsManager.getDefault().divideMessage(message);
            SmsManager.getDefault().sendMultipartTextMessage(number, null, messages, null, null);
        }

        private void saveSentSms(String phoneNumber, String message) {
            ContentValues values = new ContentValues();
            values.put(ADDRESS, phoneNumber);
            values.put(DATE, System.currentTimeMillis());
            values.put(READ, 1);
            values.put(STATUS, -1);
            values.put(TYPE, 2);
            values.put(BODY, message);
            getContentResolver().insert(Uri.parse("content://sms"), values);
        }

        private void deleteDNDManagerItem(int position) {
            viewPager.setAdapter(null);
            DNDManagerItem removedItem = pagerAdapter.removeDNDManagerItem(position);
            DNDManagerItemType itemType = removedItem.getItemType();
            if (itemType.equals(DNDManagerItemType.CALL)) {
                deleteCallLogByNumber(removedItem.getNumber());
            } else {
                deleteSmsByNumber(removedItem.getNumber());
            }
            viewPager.setAdapter(pagerAdapter);
            int pageIndex = position;
            if (pageIndex == pagerAdapter.getCount()) {
                pageIndex--;
            }
            viewPager.setCurrentItem(pageIndex);
            if (pageIndex < 0) {
                finish();
            }
        }

        public void deleteCallLogByNumber(String number) {
            try {
                String queryString = CallLog.Calls.NUMBER + " = '" + number + "'";
                getContentResolver().delete(CallLog.Calls.CONTENT_URI, queryString, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void deleteSmsByNumber(String number) {
            try {
                String queryString = "address" + " = '" + number + "'";
                getContentResolver().delete(Uri.parse("content://sms"), queryString, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private class CancelButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }
}
