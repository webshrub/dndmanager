package com.webshrub.moonwalker.androidapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Ahsan.Javed.
 */
public class DNDManagerSettingsPlugin extends Plugin {
    private static final String TAG = "SettingsPlugin";
    private static final String SET_PREFERENCE = "set_preference";

    @Override
    public PluginResult execute(String action, JSONArray data, String callbackId) {
        Log.d(TAG, "SettingsPlugin Called");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.cordova.getActivity());
        PluginResult result;
        if (SET_PREFERENCE.equals(action)) {
            try {
                String key = data.getString(0);
                String value = data.getString(1);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if ("true".equals(value.toLowerCase()) || "false".equals(value.toLowerCase())) {
                    editor.putBoolean(key, Boolean.parseBoolean(value));
                } else {
                    editor.putString(key, value);
                }
                Log.d(TAG, "SettingsPlugin Called with key = " + key + " value = " + value);
                result = new PluginResult(PluginResult.Status.OK, editor.commit());
            } catch (JSONException e) {
                Log.d(TAG, "Got JSON Exception " + e.getMessage());
                result = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
            }
        } else {
            result = new PluginResult(PluginResult.Status.INVALID_ACTION);
            Log.d(TAG, "Invalid action : " + action + " passed");
        }
        return result;
    }
}
