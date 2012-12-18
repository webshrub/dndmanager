package com.phonegap.plugins.pushnotifications;

import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMRegistrar;
import com.phonegap.plugins.pushnotifications.gcm.PushManager;
import org.apache.cordova.api.Plugin;
import org.apache.cordova.api.PluginResult;
import org.apache.cordova.api.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PushNotifications extends Plugin
{
    public static final String REGISTER = "registerDevice";
    public static final String UNREGISTER = "unregisterDevice";

    HashMap<String, String> callbackIds = new HashMap<String, String>();

    /**
     * Called when the activity receives a new intent.
     */
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        checkMessage(intent);
    }

    @Override
    public PluginResult execute(String action, JSONArray data, String callbackId)
    {
        Log.d("PushNotifications", "Plugin Called");

        PluginResult result = null;
        if (REGISTER.equals(action))
        {
            callbackIds.put("registerDevice", callbackId);

            JSONObject params = null;
            try
            {
                params = data.getJSONObject(0);
            } catch (JSONException e)
            {
            	e.printStackTrace();
                return new PluginResult(Status.ERROR);
            }
            PushManager mPushManager = null;
            try
            {
                mPushManager = new PushManager(cordova.getContext(), params.getBoolean("alert"), params.getBoolean("badge"), params.getBoolean("sound"), params.getString("senderid"));
            } catch (JSONException e)
            {
            	e.printStackTrace();
                return new PluginResult(Status.ERROR);
            }

            try
            {
                mPushManager.onStartup(null, cordova.getContext());
            } catch (RuntimeException e)
            {
            	e.printStackTrace();
                return new PluginResult(Status.ERROR);
            }

            checkMessage(cordova.getActivity().getIntent());

            result = new PluginResult(Status.NO_RESULT);
            result.setKeepCallback(true);

            return result;
        }

        if (UNREGISTER.equals(action))
        {
            callbackIds.put("unregisterDevice", callbackId);
            result = new PluginResult(Status.NO_RESULT);
            result.setKeepCallback(true);

            try
            {
                GCMRegistrar.unregister(cordova.getContext());
            } catch (Exception e)
            {
                return new PluginResult(Status.ERROR);
            }

            return result;
        }

        Log.d("DirectoryListPlugin", "Invalid action : " + action + " passed");
        return new PluginResult(Status.INVALID_ACTION);
    }

    private void checkMessage(Intent intent)
    {
        if (null != intent)
        {
            if (intent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
            {
                doOnMessageReceive(intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT));
            }
            else if (intent.hasExtra(PushManager.REGISTER_EVENT))
            {
                doOnRegistered(intent.getExtras().getString(PushManager.REGISTER_EVENT));
            }
            else if (intent.hasExtra(PushManager.UNREGISTER_EVENT))
            {
                doOnUnregisteredError(intent.getExtras().getString(PushManager.UNREGISTER_EVENT));
            }
            else if (intent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
            {
                doOnRegisteredError(intent.getExtras().getString(PushManager.REGISTER_ERROR_EVENT));
            }
            else if (intent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
            {
                doOnUnregistered(intent.getExtras().getString(PushManager.UNREGISTER_ERROR_EVENT));
            }
        }
    }

    public void doOnRegistered(String registrationId)
    {
        String callbackId = callbackIds.get("registerDevice");
        PluginResult result = new PluginResult(Status.OK, registrationId);
        success(result, callbackId);
    }

    public void doOnRegisteredError(String errorId)
    {
        String callbackId = callbackIds.get("registerDevice");
        PluginResult result = new PluginResult(Status.OK, errorId);
        error(result, callbackId);
    }

    public void doOnUnregistered(String registrationId)
    {
        String callbackId = callbackIds.get("unregisterDevice");
        PluginResult result = new PluginResult(Status.OK, registrationId);
        success(result, callbackId);
    }

    public void doOnUnregisteredError(String errorId)
    {
        String callbackId = callbackIds.get("unregisterDevice");
        PluginResult result = new PluginResult(Status.OK, errorId);
        error(result, callbackId);
    }

    public void doOnMessageReceive(String message)
    {
        String jsStatement = String.format("window.plugins.pushNotification.notificationCallback(%s);", message);
        sendJavascript(jsStatement);
    }
}
