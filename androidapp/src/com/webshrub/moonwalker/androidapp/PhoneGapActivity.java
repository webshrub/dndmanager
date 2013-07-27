package com.webshrub.moonwalker.androidapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import com.phonegap.plugins.pushnotifications.gcm.PushManager;
import com.sbstrm.appirater.Appirater;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


public class PhoneGapActivity extends org.apache.cordova.DroidGap {
    /**
     * Intent used to display a message in the screen.
     */
    static final String DISPLAY_MESSAGE_ACTION =
            "com.google.android.gcm.demo.app.DISPLAY_MESSAGE";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    static final String EXTRA_MESSAGE = "message";

    private static final String WORK_DIR = "file:///android_asset/www/";

    protected float ORIG_APP_W = 400;
    protected float ORIG_APP_H = 800;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
     *                           is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Appirater.appLaunched(this);
        super.loadUrl(WORK_DIR + getStartFileName());
        super.appView.setVerticalScrollBarEnabled(false);
        super.appView.setHorizontalScrollBarEnabled(false);
        super.setIntegerProperty("loadUrlTimeoutValue", 60000);
        this.appView.clearCache(false);
        this.appView.clearHistory();
        // set some defaults
        //this.appView.setBackgroundColor(0x000000);
        this.appView.setHorizontalScrollBarEnabled(false);
        this.appView.setHorizontalScrollbarOverlay(false);
        this.appView.setVerticalScrollBarEnabled(false);
        this.appView.setVerticalScrollbarOverlay(false);
        // get actual screen size
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        // calculate target scale (only dealing with portrait orientation)
        double globalScale = Math.ceil((width / ORIG_APP_W) * 100);
        // make sure we're all good
        Log.v("ORIG_APP_W", " = " + ORIG_APP_W);
        Log.v("ORIG_APP_H", " = " + ORIG_APP_H);
        Log.v("width", " = " + width);
        Log.v("this.appView.getMeasuredHeight()", " = " + height);
        Log.v("globalScale", " = " + globalScale);
        Log.v("this.appView.getScale()", "index=" + this.appView.getScale());
        // set some defaults on the web view
        this.appView.getSettings().setBuiltInZoomControls(false);
        this.appView.getSettings().setSupportZoom(true);
        this.appView.getSettings().setGeolocationEnabled(true);
        this.appView.getSettings().setLightTouchEnabled(false);
        // caching is preventin android 2.3.3 from working properly with REST calls (ETST-5834, ETST-6716)
        this.appView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        this.appView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // set the scale this.appView.setInitialScale((int) globalScale);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        checkMessage(intent);
    }

    /**
     * overrided to avoid backing to first screen
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // do nothing
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // do your task
        }
        super.onConfigurationChanged(newConfig);
    }

    private String getStartFileName() {
        String fileName = "index.html";
        try {
            InputStream fstream = getAssets().open("www/descriptor.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = br.readLine();
            if (strLine != null) {
                fileName = strLine.trim();
            }
            in.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return fileName;
    }

    private void checkMessage(Intent intent) {
        if (null != intent) {
            if (intent.hasExtra(PushManager.PUSH_RECEIVE_EVENT)) {
                sendJavascript(getJSFunction("onPushReceive", intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT)));
            } else if (intent.hasExtra(PushManager.REGISTER_EVENT)) {
                sendJavascript(getJSFunction("onRegister", intent.getExtras().getString(PushManager.REGISTER_EVENT)));
            } else if (intent.hasExtra(PushManager.UNREGISTER_EVENT)) {
                sendJavascript(getJSFunction("onUnregister", intent.getExtras().getString(PushManager.UNREGISTER_EVENT)));
            } else if (intent.hasExtra(PushManager.REGISTER_ERROR_EVENT)) {
                sendJavascript(getJSFunction("onRegisterError", intent.getExtras().getString(PushManager.REGISTER_ERROR_EVENT)));
            }
        }
    }

    private String getJSFunction(String functionName, String string) {
        return "javascript:" + functionName + "(\"" + string.replaceAll("\"", "'") + "\");";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //GCMRegistrar.onDestroy(this);
    }
}
