package com.webshrub.moonwalker.androidapp;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CellularNetwork extends Plugin {

    private static final String TAG = "CallLogPlugin";

    @Override
    public PluginResult execute(String action, JSONArray data, String callbackId) {
        Log.d(TAG, "Plugin Called");
        PluginResult result = null;
        JSONObject messages = new JSONObject();
        messages = getOperator();
        result = new PluginResult(PluginResult.Status.OK, messages);
        return result;
    }


    public JSONObject getOperator() {
        JSONObject data = new JSONObject();
        try {
            TelephonyManager manager = (TelephonyManager) ctx.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            String opeartorName = manager.getNetworkOperatorName();
            data.put("opeartorName", opeartorName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}

