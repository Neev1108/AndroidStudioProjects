package edu.sjsu.android.map;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LocationsContentProvider extends ContentProvider {
    public static final String provider_name = "edu.sjsu.android.map.locations";
    public static final Uri content_uri = Uri.parse("content://" + provider_name + "/locations");
    public static final int locations_instance = 1;
    private static final UriMatcher uri_matcher = new UriMatcher(UriMatcher.NO_MATCH);;

    static {
        uri_matcher.addURI(provider_name, "locations", 1);
    }

    LocationsDB db;

    @Override
    public boolean onCreate() {
        db = new LocationsDB(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if(uri_matcher.match(uri)==locations_instance){
            return db.getAll();
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long row_id = db.insert(values);

        if (row_id > 0){
            return ContentUris.withAppendedId(content_uri, row_id);
        }
        else {
            return null;
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return db.delete();
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
