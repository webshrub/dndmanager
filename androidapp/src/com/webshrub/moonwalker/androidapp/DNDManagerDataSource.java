package com.webshrub.moonwalker.androidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.webshrub.moonwalker.androidapp.DNDManagerSQLiteHelper.COLUMNS_IGNORED_CONTACT;
import static com.webshrub.moonwalker.androidapp.DNDManagerSQLiteHelper.COLUMN_CACHED_NAME;
import static com.webshrub.moonwalker.androidapp.DNDManagerSQLiteHelper.COLUMN_ID;
import static com.webshrub.moonwalker.androidapp.DNDManagerSQLiteHelper.COLUMN_NUMBER;
import static com.webshrub.moonwalker.androidapp.DNDManagerSQLiteHelper.TABLE_IGNORED_CONTACT;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: 5/6/13
 * Time: 10:16 PM
 */
public class DNDManagerDataSource {
    private static DNDManagerDataSource instance;
    private SQLiteDatabase database;

    private DNDManagerDataSource(Context context) {
        DNDManagerSQLiteHelper helper = new DNDManagerSQLiteHelper(context);
        database = helper.getWritableDatabase();
    }

    //no synchronized???
    public static DNDManagerDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new DNDManagerDataSource(context);
        }
        return instance;
    }

    public long createIgnoredContact(DNDManagerIgnoredContact ignoredContact) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NUMBER, ignoredContact.getNumber());
        values.put(COLUMN_CACHED_NAME, ignoredContact.getCachedName());
        return database.insert(TABLE_IGNORED_CONTACT, null, values);
    }

    public void deleteIgnoredContact(DNDManagerIgnoredContact ignoredContact) {
        long id = ignoredContact.getId();
        database.delete(TABLE_IGNORED_CONTACT, COLUMN_ID + " = " + id, null);
    }

    public List<DNDManagerIgnoredContact> getAllIgnoredContacts() {
        List<DNDManagerIgnoredContact> ignoredContacts = new ArrayList<DNDManagerIgnoredContact>();
        Cursor cursor = database.query(TABLE_IGNORED_CONTACT, COLUMNS_IGNORED_CONTACT, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DNDManagerIgnoredContact ignoredContact = cursorToIgnoredContact(cursor);
            ignoredContacts.add(ignoredContact);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return ignoredContacts;
    }

    private DNDManagerIgnoredContact cursorToIgnoredContact(Cursor cursor) {
        DNDManagerIgnoredContact ignoredContact = new DNDManagerIgnoredContact();
        ignoredContact.setId(cursor.getLong(0));
        ignoredContact.setNumber(cursor.getString(1));
        ignoredContact.setCachedName(cursor.getString(2));
        return ignoredContact;
    }
}
