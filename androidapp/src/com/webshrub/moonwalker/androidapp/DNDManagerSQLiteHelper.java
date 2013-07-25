package com.webshrub.moonwalker.androidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DNDManagerSQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dnd_manager.db";
    public static final String TABLE_IGNORED_CONTACT = "ignored_contact";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_CACHED_NAME = "cached_name";

    public static final String[] COLUMNS_IGNORED_CONTACT = {
            COLUMN_ID,
            COLUMN_NUMBER,
            COLUMN_CACHED_NAME
    };

    public static final String CREATE_IGNORED_CONTACT = "create table " + TABLE_IGNORED_CONTACT + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NUMBER + " text, "
            + COLUMN_CACHED_NAME + " text "
            + ");";

    public DNDManagerSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_IGNORED_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DNDManagerSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IGNORED_CONTACT);
        onCreate(db);
    }
}