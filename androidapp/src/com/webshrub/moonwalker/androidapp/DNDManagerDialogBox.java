package com.webshrub.moonwalker.androidapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.TRAI_CONTACT_NUMBER;

public class DNDManagerDialogBox extends FragmentActivity {
    private ViewPager viewPager;
    private DNDManagerItemPagerAdapter pagerAdapter;
    private Button cancelButton;
    private Button reportSpamButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogbox);
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new DNDManagerItemPagerAdapter(this, getContactLogFlag());
        viewPager.setAdapter(pagerAdapter);
        cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new CancelButtonOnClickListener());
        reportSpamButton = (Button) findViewById(R.id.reportSpam);
        reportSpamButton.setOnClickListener(new ReportSpamButtonOnClickListener());
    }

    private boolean getContactLogFlag() {
        return true;
    }

    private class ReportSpamButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            DNDManagerItem dndManagerItem = pagerAdapter.getDNDManagerItem(viewPager.getCurrentItem());
            String number = dndManagerItem.getNumber();
            String dateTime = dndManagerItem.getDateTime();
            String shortDescription = DNDManagerUtil.stripText(dndManagerItem.getText());
            String messageText = DNDManagerUtil.getMessageText(number, dateTime, shortDescription);
            sendSMS(TRAI_CONTACT_NUMBER, messageText);
            if (getDeleteDNDManagerItemFlag()) {
                deleteDNDManagerItem(viewPager.getCurrentItem());
            }
            Toast toast = Toast.makeText(DNDManagerDialogBox.this, "Your request has been submitted successfully.", Toast.LENGTH_SHORT);
            toast.show();
        }

        private void sendSMS(String number, String message) {
            ArrayList<String> messages = SmsManager.getDefault().divideMessage(message);
            SmsManager.getDefault().sendMultipartTextMessage(number, null, messages, null, null);
        }

        private void deleteDNDManagerItem(int position) {
            viewPager.setAdapter(null);
            pagerAdapter.removeDNDManagerItem(position);
            viewPager.setAdapter(pagerAdapter);
            int pageIndex = position;
            if (pageIndex == pagerAdapter.getCount()) {
                pageIndex--;
            }
            viewPager.setCurrentItem(pageIndex);
            if (pageIndex < 0) {
                reportSpamButton.setEnabled(false);
            }
        }

        private boolean getDeleteDNDManagerItemFlag() {
            return true;
        }

        private boolean getDeleteSentSMSFlag() {
            return true;
        }
    }

    private class CancelButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }
}
