package com.webshrub.moonwalker.androidapp;

public class DNDManagerHtmlHelper {
    public static boolean getContactLogFlag() {
        return true;
    }

    public static boolean getDeleteDNDManagerItemFlag() {
        return true;
    }

    public static boolean getDeleteSentSMSFlag() {
        return false;
    }

    public static String getMessageFormat() {
        return "COMP TEL NO {number};{dateTime};{shortDescription}";
    }
}
