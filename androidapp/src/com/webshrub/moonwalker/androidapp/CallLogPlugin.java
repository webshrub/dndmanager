package com.webshrub.moonwalker.androidapp;

import android.util.Log;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONObject;

public class CallLogPlugin extends Plugin {
    private static final String ACTION = "list";
    private static final String TAG = "CallLogPlugin";

    @Override
    public PluginResult execute(String action, JSONArray data, String callbackId) {
        Log.d(TAG, "Plugin Called");
        PluginResult result;
        if (ACTION.equals(action)) {
            try {
                DNDManagerItemManager dndManagerItemManager = new DNDManagerItemManager(this.cordova.getActivity());
                JSONObject json = dndManagerItemManager.getListingAsJSON(DNDManagerItemType.CALL);
                result = new PluginResult(Status.OK, json);
            } catch (Exception e) {
                Log.d(TAG, "Got Exception " + e.getMessage());
                result = new PluginResult(Status.JSON_EXCEPTION);
            }
        } else {
            result = new PluginResult(Status.INVALID_ACTION);
            Log.d(TAG, "Invalid action : " + action + " passed");
        }
        return result;
    }
}

