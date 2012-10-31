package com.webshrub.moonwalker.androidapp;

import android.os.Bundle;
import org.apache.cordova.DroidGap;

public class HomeActivity extends DroidGap {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        Toast.makeText(getApplicationContext(), "Starting Application", Toast.LENGTH_SHORT).show();

        super.loadUrl("file:///android_asset/www/index.html");
//        Toast.makeText(getApplicationContext(), "Application started.", Toast.LENGTH_SHORT).show();

    }
}
