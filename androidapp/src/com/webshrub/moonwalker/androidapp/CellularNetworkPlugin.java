package com.webshrub.moonwalker.androidapp;

import android.content.Context;
import android.telephony.TelephonyManager;

import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CellularNetworkPlugin extends Plugin {

    @Override
    public PluginResult execute(String action, JSONArray data, String callbackId) {
        JSONObject operatorInfo = getOperatorInfo();
        return new PluginResult(PluginResult.Status.OK, operatorInfo);
    }


    public JSONObject getOperatorInfo() {
        JSONObject data = new JSONObject();
        try {
            TelephonyManager manager = (TelephonyManager) ctx.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            String simOperator = manager.getSimOperator();
            if (simOperator != null) {
                int mcc = Integer.parseInt(simOperator.substring(0, 3));
                data.put("mcc", mcc);
                int mnc = Integer.parseInt(simOperator.substring(3));
                data.put("mnc", mnc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}

