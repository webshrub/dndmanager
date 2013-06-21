package com.webshrub.moonwalker.androidapp;

public class DNDManagerUtil {
    public static String stripText(String input) {
        if (input.length() < 115) {
            return input;
        }
        return input.substring(0, 115);
    }

    public static String getMessageText(String number, String dateTime, String shortDescription) {
        String messageText = DNDManagerHtmlHelper.getMessageFormat().replaceAll("\\{number\\}", number);
        messageText = messageText.replaceAll("\\{dateTime\\}", dateTime);
        messageText = messageText.replaceAll("\\{shortDescription\\}", shortDescription);
        return messageText;
    }
}
