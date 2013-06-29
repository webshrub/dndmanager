package com.webshrub.moonwalker.androidapp;

public enum DNDManagerItemType {
    CALL(0),
    SMS(1);
    private int ordinal;

    DNDManagerItemType(int ordinal) {
        this.ordinal = ordinal;
    }

    private int getOrdinal() {
        return ordinal;
    }
}
