package com.webshrub.moonwalker.androidapp;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;

import static com.webshrub.moonwalker.androidapp.DNDManagerConstants.TRAI_CONTACT_NUMBER;

public class SmsPlugin extends Plugin {
    private static final String ACTION_SEND_SMS = "SendSMS";
    private static final String CALL = "call";
    private static final String SMS = "sms";

    @Override
    public PluginResult execute(String action, JSONArray arg1, String callbackId) {
        PluginResult result = new PluginResult(Status.INVALID_ACTION);
        if (action.equalsIgnoreCase(ACTION_SEND_SMS)) {
            try {
                String message = arg1.getString(1);
                DNDManagerUtil.sendSMS(this.cordova.getActivity(), TRAI_CONTACT_NUMBER, message);
                String reportType = arg1.getString(2);
                String spamNumber = arg1.getString(5);
                if (DNDManagerHtmlHelper.getDeleteDNDManagerItemFlag(this.cordova.getActivity())) {
                    if (CALL.equalsIgnoreCase(reportType)) {
                        DNDManagerUtil.deleteCallLogByNumber(this.cordova.getActivity(), spamNumber);
                    } else if (SMS.equalsIgnoreCase(reportType)) {
                        DNDManagerUtil.deleteSmsByNumber(this.cordova.getActivity(), spamNumber);
                    }
                }
                if (DNDManagerHtmlHelper.getDeleteSentSMSFlag(this.cordova.getActivity())) {
                    DNDManagerUtil.saveSentSms(this.cordova.getActivity(), TRAI_CONTACT_NUMBER, message);
                }
                result = new PluginResult(Status.OK);
            } catch (JSONException ex) {
                result = new PluginResult(Status.JSON_EXCEPTION, ex.getMessage());
            }
        }
        return result;
    }
}
