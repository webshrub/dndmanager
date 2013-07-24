package com.webshrub.moonwalker.androidapp;

import android.util.Log;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;

public class SMSReaderPlugin extends Plugin {
    private static final String ACTION = "inbox";
    private static final String TAG = "SMSReaderPlugin";

    @Override
    public PluginResult execute(String action, JSONArray data, String callbackId) {
        Log.d(TAG, "Plugin Called");
        PluginResult result;
        if (ACTION.equals(action)) {
            try {
                DNDManagerItemManager dndManagerItemManager = new DNDManagerItemManager(this.cordova.getActivity());
                JSONObject json = dndManagerItemManager.getListingAsJSON(DNDManagerItemType.SMS);
                result = new PluginResult(PluginResult.Status.OK, json);
            } catch (Exception e) {
                Log.d(TAG, "Got Exception " + e.getMessage());
                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }
        } else {
            result = new PluginResult(PluginResult.Status.INVALID_ACTION);
            Log.d(TAG, "Invalid action : " + action + " passed");
        }
        return result;
    }
}
