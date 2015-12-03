package com.federicobarocci.wifiexplorer.model.db.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.federicobarocci.wifiexplorer.model.location.LocationElement;
import com.federicobarocci.wifiexplorer.model.location.hashmap.LocationMap;
import com.federicobarocci.wifiexplorer.model.wifi.WifiElement;
import com.federicobarocci.wifiexplorer.model.wifi.container.strategy.sortedlist.WifiList;

import javax.inject.Inject;

/**
 * Created by Federico
 */
public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WifiExplorer.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_WIFI = "wifi";
    public static final String FIELD_BSSID = "bssid";
    public static final String FIELD_SSID = "ssid";
    public static final String FIELD_CAPABILITIES = "capabilities";
    public static final String FIELD_LAT = "latitude";
    public static final String FIELD_LONG = "longitude";
    public static final String FIELD_RADIUS = "radius";

    private static final String TABLE_WIFI_CREATE =
            String.format("create table %s (%s text primary key, %s text, %s text, %s real, %s real, %s real)",
                    TABLE_WIFI, FIELD_BSSID, FIELD_SSID, FIELD_CAPABILITIES, FIELD_LAT, FIELD_LONG, FIELD_RADIUS);

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

    public WifiList selectWifiElements() {
        SQLiteDatabase db = this.getReadableDatabase();

        final String query = String.format("select %s, %s, %s from %s",
                FIELD_BSSID, FIELD_SSID, FIELD_CAPABILITIES, TABLE_WIFI);

        Cursor c = db.rawQuery(query, null);
        WifiList list = new WifiList();

        if (c != null && c.moveToFirst()) {
            do {
                list.add(new WifiElement(
                        c.getString(c.getColumnIndex(FIELD_BSSID)),
                        c.getString(c.getColumnIndex(FIELD_SSID)),
                        c.getString(c.getColumnIndex(FIELD_CAPABILITIES))));
            } while (c.moveToNext());

            c.close();
        }

        return list;
    }

    public LocationMap selectLocationElements() {
        SQLiteDatabase db = this.getReadableDatabase();

        final String query = String.format("select %s, %s, %s, %s from %s",
                FIELD_BSSID, FIELD_LAT, FIELD_LONG, FIELD_RADIUS, TABLE_WIFI);

        Cursor c = db.rawQuery(query, null);
        LocationMap map = new LocationMap();

        if (c != null && c.moveToFirst()) {
            do {
                map.put(c.getString(c.getColumnIndex(FIELD_BSSID)),
                        c.getDouble(c.getColumnIndex(FIELD_LAT)),
                        c.getDouble(c.getColumnIndex(FIELD_LONG)),
                        c.getDouble(c.getColumnIndex(FIELD_RADIUS)));
            } while (c.moveToNext());

            c.close();
        }

        return map;
    }

    public long insert(WifiElement wifiElement, LocationElement locationElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_BSSID, wifiElement.getBSSID());
        contentValues.put(FIELD_SSID, wifiElement.getSSID());
        contentValues.put(FIELD_CAPABILITIES, wifiElement.getCapabilities());
        contentValues.put(FIELD_LAT, locationElement.getLocation().latitude);
        contentValues.put(FIELD_LONG, locationElement.getLocation().longitude);
        contentValues.put(FIELD_RADIUS, locationElement.getRadius());

        return db.insert(TABLE_WIFI, null, contentValues);
    }

    public long insert(DataBaseElement dataBaseElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_BSSID, dataBaseElement.bssid);
        contentValues.put(FIELD_SSID, dataBaseElement.ssid);
        contentValues.put(FIELD_CAPABILITIES, dataBaseElement.capabilities);
        contentValues.put(FIELD_LAT, dataBaseElement.latitude);
        contentValues.put(FIELD_LONG, dataBaseElement.longitude);
        contentValues.put(FIELD_RADIUS, dataBaseElement.radius);

        return db.insert(TABLE_WIFI, null, contentValues);
    }

    public long insert(WifiElement wifiElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_BSSID, wifiElement.getBSSID());
        contentValues.put(FIELD_SSID, wifiElement.getSSID());
        contentValues.put(FIELD_CAPABILITIES, wifiElement.getCapabilities());

        return db.insert(TABLE_WIFI, null, contentValues);
    }

    public DataBaseElement delete(String bssid) {
        final DataBaseElement dataBaseElement = select(bssid);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WIFI, FIELD_BSSID + " = ?", new String[]{bssid});

        return dataBaseElement;
    }

    private DataBaseElement select(String bssid) {
        SQLiteDatabase db = this.getReadableDatabase();
        final String query = String.format("select * from %s where %s = '%s'", TABLE_WIFI, FIELD_BSSID, bssid);

        Cursor c = db.rawQuery(query, null);
        DataBaseElement dataBaseElement = null;

        if (c != null && c.moveToFirst()) {
            dataBaseElement = new DataBaseElement(
                    c.getString(c.getColumnIndex(FIELD_BSSID)),
                    c.getString(c.getColumnIndex(FIELD_SSID)),
                    c.getString(c.getColumnIndex(FIELD_CAPABILITIES)),
                    c.getDouble(c.getColumnIndex(FIELD_LAT)),
                    c.getDouble(c.getColumnIndex(FIELD_LONG)),
                    c.getDouble(c.getColumnIndex(FIELD_RADIUS)));

            c.close();
        }

        return dataBaseElement;
    }

    public int update(WifiElement wifiElement, LocationElement locationElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_SSID, wifiElement.getSSID());
        contentValues.put(FIELD_CAPABILITIES, wifiElement.getCapabilities());
        contentValues.put(FIELD_LAT, locationElement.getLocation().latitude);
        contentValues.put(FIELD_LONG, locationElement.getLocation().longitude);
        contentValues.put(FIELD_RADIUS, locationElement.getRadius());

        return db.update(TABLE_WIFI, contentValues, FIELD_BSSID + " = ?", new String[]{wifiElement.getBSSID()});
    }

    public int update(WifiElement wifiElement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_SSID, wifiElement.getSSID());
        contentValues.put(FIELD_CAPABILITIES, wifiElement.getCapabilities());

        return db.update(TABLE_WIFI, contentValues, FIELD_BSSID + " = ?", new String[]{wifiElement.getBSSID()});
    }
}
