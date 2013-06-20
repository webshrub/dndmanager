package com.webshrub.moonwalker.androidapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: WS1
 * Date: 5/10/13
 * Time: 3:26 PM
 */
public class DNDManagerItem implements Comparable<DNDManagerItem> {
    private static final SimpleDateFormat SIMPLE_DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yy;kk:mm");
    private String date;
    private String dateTime;
    private String number;
    private String cachedName;
    private String text;
    private DNDManagerItemType itemType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCachedName() {
        return cachedName;
    }

    public void setCachedName(String cachedName) {
        this.cachedName = cachedName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DNDManagerItemType getItemType() {
        return itemType;
    }

    public void setItemType(DNDManagerItemType itemType) {
        this.itemType = itemType;
    }

    @Override
    public int compareTo(DNDManagerItem item) {
        try {
            Date date1 = SIMPLE_DATE_TIME_FORMAT.parse(dateTime);
            Date date2 = SIMPLE_DATE_TIME_FORMAT.parse(item.getDateTime());
            return date2.compareTo(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}