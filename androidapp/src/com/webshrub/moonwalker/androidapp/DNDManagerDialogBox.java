package com.webshrub.moonwalker.androidapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;

import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.ADDRESS;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.BODY;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.DATE;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.READ;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.STATUS;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.TRAI_CONTACT_NUMBER;
import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.TYPE;

public class DNDManagerDialogBox extends FragmentActivity {
    private static final int REPORT_SPAM_DIALOG = 0;
    private ViewPager viewPager;
    private DNDManagerItemPagerAdapter pagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new DNDManagerItemPagerAdapter(this, DNDManagerHtmlHelper.getContactLogFlag());
        if (pagerAdapter.getCount() == 0) {
            Toast.makeText(DNDManagerDialogBox.this, "Hurray! No spam calls/sms in your inbox.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(DNDManagerDialogBox.this, "Showing only last 3 day's calls and sms as per TRAI guidelines.", Toast.LENGTH_LONG).show();
        }
        showDialog(REPORT_SPAM_DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case REPORT_SPAM_DIALOG: {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialogbox);
                dialog.setCancelable(false);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setTitle("DND Manager " + "(Showing  1/" + pagerAdapter.getCount() + ")");
                viewPager = (ViewPager) dialog.findViewById(R.id.pager);
                viewPager.setAdapter(pagerAdapter);
                UnderlinePageIndicator pageIndicator = (UnderlinePageIndicator) dialog.findViewById(R.id.pageIndicator);
                pageIndicator.setViewPager(viewPager);
                pageIndicator.setFades(false);
                pageIndicator.setOnPageChangeListener(new DNDManagerOnPageChangeListener(dialog));
                Button reportSpamButton = (Button) dialog.findViewById(R.id.reportSpam);
                if (pagerAdapter.getCount() == 0) {
                    reportSpamButton.setEnabled(false);
                } else {
                    reportSpamButton.setOnClickListener(new ReportSpamButtonOnClickListener());
                }
                dialog.findViewById(R.id.cancel).setOnClickListener(new CancelButtonOnClickListener());
                return dialog;
            }
            default:
                return null;
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

    private class DNDManagerOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private Dialog dialog;

        public DNDManagerOnPageChangeListener(Dialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            int currentIndex = position + 1;
            dialog.setTitle("DND Manager " + "(Showing " + currentIndex + "/" + pagerAdapter.getCount() + ")");
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
