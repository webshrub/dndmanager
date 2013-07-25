package com.webshrub.moonwalker.androidapp;

import android.util.Log;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class IgnoredContactsPlugin extends Plugin {
    private static final String LIST = "list";
    private static final String DELETE = "delete";
    private static final String TAG = "IgnoredContactsPlugin";

    @Override
    public PluginResult execute(String action, JSONArray data, String callbackId) {
        Log.d(TAG, "Plugin Called");
        PluginResult result;
        try {
            if (LIST.equals(action)) {
                DNDManagerDataSource dataSource = DNDManagerDataSource.getInstance(this.cordova.getActivity());
                List<DNDManagerIgnoredContact> ignoredContactList = dataSource.getAllIgnoredContacts();
                JSONObject jsonObject = new JSONObject();
                if (ignoredContactList.size() == 0) {
                    jsonObject.put("rows", new JSONArray());
                } else {
                    JSONObject row = new JSONObject();
                    JSONArray rows = new JSONArray();
                    for (DNDManagerIgnoredContact ignoredContact : ignoredContactList) {
                        row.put("id", ignoredContact.getId());
                        row.put("number", ignoredContact.getNumber());
                        row.put("cachedName", ignoredContact.getCachedName());
                        rows.put(row);
                        row = new JSONObject();
                    }
                    jsonObject.put("rows", rows);
                }
                result = new PluginResult(Status.OK, jsonObject);
            } else if (DELETE.equals(action)) {
                DNDManagerIgnoredContact ignoredContact = new DNDManagerIgnoredContact();
                ignoredContact.setId(data.getLong(0));
                DNDManagerDataSource dataSource = DNDManagerDataSource.getInstance(this.cordova.getActivity());
                dataSource.deleteIgnoredContact(ignoredContact);
                result = new PluginResult(Status.OK);
            } else {
                result = new PluginResult(Status.INVALID_ACTION);
                Log.d(TAG, "Invalid action : " + action + " passed");
            }
        } catch (Exception e) {
            Log.d(TAG, "Got Exception " + e.getMessage());
            result = new PluginResult(Status.JSON_EXCEPTION);
        }
        return result;
    }
}

