package com.webshrub.moonwalker.androidapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahsan.Javed.
 */
public class DNDManagerIgnoredContact implements Parcelable {
    private long id;
    private String number;
    private String cachedName;

    public DNDManagerIgnoredContact() {
    }

    public DNDManagerIgnoredContact(Parcel in) {
        readFromParcel(in);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(number);
        dest.writeString(cachedName);
    }

    private void readFromParcel(Parcel in) {
        id = in.readLong();
        number = in.readString();
        cachedName = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public DNDManagerIgnoredContact createFromParcel(Parcel in) {
            return new DNDManagerIgnoredContact(in);
        }

        public DNDManagerIgnoredContact[] newArray(int size) {
            return new DNDManagerIgnoredContact[size];
        }
    };
}
