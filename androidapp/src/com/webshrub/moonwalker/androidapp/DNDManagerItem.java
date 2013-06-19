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
    private int type;
    private long duration;
    private int newFlag;
    private String cachedName;
    private int cachedNumberType;
    private String text;
    private String id;
    private DNDManagerItemType itemType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getNewFlag() {
        return newFlag;
    }

    public void setNewFlag(int newFlag) {
        this.newFlag = newFlag;
    }

    public String getCachedName() {
        return cachedName;
    }

    public void setCachedName(String cachedName) {
        this.cachedName = cachedName;
    }

    public int getCachedNumberType() {
        return cachedNumberType;
    }

    public void setCachedNumberType(int cachedNumberType) {
        this.cachedNumberType = cachedNumberType;
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