package edu.sjsu.android.googlemaps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocationsDB extends SQLiteOpenHelper {

    public static final String db_name = "LocationDB";
    public static final String table = "locations";
    public static final String id = "ID";
    public static final String lon = "Longitude";
    public static final String lat = "Latitude";
    public static final String zoom = "Zoom_level";
    private SQLiteDatabase db;

    public LocationsDB(Context context) {
        super(context, db_name, null, 1);
        this.db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + table + " ( " + id + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                lon + " DOUBLE , " + lat + " DOUBLE , " + zoom + " TEXT);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public long insert(ContentValues contentValues){
        long rowID = db.insert(table, null, contentValues);
        return rowID;
    }

    public int delete(){
        int tb = db.delete(table, null, null);
        return tb;
    }

    public Cursor getAll(){
        return db.query(table, new String[] { id, lon, lat, zoom}, null, null, null, null, null);
    }
}