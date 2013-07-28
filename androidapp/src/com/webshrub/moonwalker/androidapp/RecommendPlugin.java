package com.webshrub.moonwalker.androidapp;

import android.content.Intent;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;

public class RecommendPlugin extends Plugin {
    private static final String ACTION_SHARE = "share";

    @Override
    public PluginResult execute(String action, JSONArray arg1, String callbackId) {
        PluginResult result = new PluginResult(Status.INVALID_ACTION);
        if (action.equalsIgnoreCase(ACTION_SHARE)) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "DND Manager. Stopping spams the right way");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Friends, I am using DND Manager to stop pesky SMS and Calls.\n" +
                    "Download it from\n" +
                    "http://play.google.com/store/apps/details?id=com.webshrub.moonwalker.androidapp");
            this.cordova.getActivity().startActivity(shareIntent);
            result = new PluginResult(Status.OK);
        }
        return result;
    }
}
