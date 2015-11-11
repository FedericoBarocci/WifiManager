package com.federicobarocci.wifimanager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by federico on 10/11/15.
 */
public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WifiManager.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_WIFI = "wifi";
    public static final String FIELD_BSSID = "bssid";
    public static final String FIELD_SSID = "ssid";
    public static final String FIELD_CAPABILITIES = "capabilities";
    public static final String FIELD_FREQUENCY = "frequency";
    public static final String FIELD_LEVEL = "level";

    private static final String TABLE_WIFI_CREATE =
            String.format("create table %s (%s text primary key, %s text, %s text, %s integer, %s integer)",
                    TABLE_WIFI, FIELD_BSSID, FIELD_SSID, FIELD_CAPABILITIES, FIELD_FREQUENCY, FIELD_LEVEL);

    @Inject
    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_WIFI_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(DataBaseManager.class.getName(), "Upgrade db " + oldVersion + " to " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI);
        onCreate(db);
    }

    public List<Pair<String, WifiElement>> select() {
        SQLiteDatabase db = this.getReadableDatabase();
        final String query = "select * from " + TABLE_WIFI;

        Cursor c = db.rawQuery(query, null);
        List<Pair<String, WifiElement>> list = new ArrayList<>();

        if (c != null && c.moveToFirst()) {
            do {
                list.add(WifiElement.create(
                        c.getString(c.getColumnIndex(FIELD_BSSID)),
                        c.getString(c.getColumnIndex(FIELD_SSID)),
                        c.getString(c.getColumnIndex(FIELD_CAPABILITIES)),
                        c.getInt(c.getColumnIndex(FIELD_FREQUENCY)),
                        c.getInt(c.getColumnIndex(FIELD_LEVEL))));
            } while (c.moveToNext());
        }

        return list;
    }

    public long insert(WifiElement wifiElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_BSSID, wifiElement.getBSSID());
        contentValues.put(FIELD_SSID, wifiElement.getSSID());
        contentValues.put(FIELD_CAPABILITIES, wifiElement.getCapabilities());
        contentValues.put(FIELD_FREQUENCY, wifiElement.getFrequency());
        contentValues.put(FIELD_LEVEL, wifiElement.getLevel());

        return db.insert(TABLE_WIFI, null, contentValues);
    }

    public void delete(WifiElement wifiElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WIFI, FIELD_BSSID + " = ?", new String[]{wifiElement.getBSSID()});
    }

    public int update(WifiElement wifiElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_SSID, wifiElement.getSSID());
        contentValues.put(FIELD_CAPABILITIES, wifiElement.getCapabilities());
        contentValues.put(FIELD_FREQUENCY, wifiElement.getFrequency());
        contentValues.put(FIELD_LEVEL, wifiElement.getLevel());

        return db.update(TABLE_WIFI, contentValues, FIELD_BSSID + " = ?", new String[]{wifiElement.getBSSID()});
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null && db.isOpen()) {
            db.close();
        }
    }
}
