package com.webshrub.moonwalker.androidapp;

import android.os.Bundle;
import org.apache.cordova.DroidGap;

public class HomeActivity extends DroidGap {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.loadUrl("file:///android_asset/www/index.html");
    }
}
