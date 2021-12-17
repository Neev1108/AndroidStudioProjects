package edu.sjsu.android.map;

import androidx.fragment.app.FragmentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.sjsu.android.map.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {

    private final LatLng LOCATION_UNIV = new LatLng(37.335371, -121.881050);
    private final LatLng LOCATION_CS = new LatLng(37.333714,-121.881860);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Crashes at map create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("Crashes at map ready");
        map = googleMap;

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // add a marker
                map.addMarker(new MarkerOptions().position(latLng));

                //create new location insert
                ContentValues contentValues = new ContentValues();
                contentValues.put(LocationsDB.lat, latLng.latitude);
                contentValues.put(LocationsDB.lon, latLng.longitude);
                contentValues.put(LocationsDB.zoom, googleMap.getCameraPosition().zoom);
                LocationInsertTask insert_task = new LocationInsertTask();
                insert_task.execute(contentValues);

                //display Maker is added to the map
                Toast.makeText(getBaseContext(), "Marker is add to the Map", Toast.LENGTH_SHORT).show();
            }
        });

        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //remove all markers
                //location delete task
                //delete all rows
                //all rows removed

                map.clear();
                LocationDeleteTask delete_task = new LocationDeleteTask();
                delete_task.execute();
                Toast.makeText(getBaseContext(), "All markers are removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            getContentResolver().insert(LocationsContentProvider.content_uri, contentValues[0]);
            return null;
        }
    }

    private class LocationDeleteTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            getContentResolver().delete(LocationsContentProvider.content_uri, null, null);
            return null;
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Loader<Cursor> c = null;

        //uri to content provider
        Uri uri = LocationsContentProvider.content_uri;
        //fetch all rows
        c = new CursorLoader(this, uri, null, null, null, null);

        return c;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> arg0, Cursor arg1) {
        int locationCount = 0;
        double lat = 0;
        double lng = 0;
        float zoom = 0;

        if(arg1 != null){
            locationCount = arg1.getCount();
            arg1.moveToFirst();
        }
        else {
            locationCount = 0;
        }
        for(int i = 0; i < locationCount; i++) {

            //get variables, create location variable, add marker, go to next row
            lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.lat));
            lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.lon));
            zoom = arg1.getFloat(arg1.getColumnIndex(LocationsDB.zoom));
            LatLng location = new LatLng(lat, lng);
            map.addMarker(new MarkerOptions().position(location));
            arg1.moveToNext();
        }

        if(locationCount > 0) {
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));
            map.animateCamera(CameraUpdateFactory.zoomTo(zoom));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public  void onClick_CS(View v){
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_CS, 18);
        map.animateCamera(update);
    }

    public  void onClick_Univ(View v){
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 14);
        map.animateCamera(update);
    }

    public void onClick_City(View v){
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV,10);
        map.animateCamera(update);
    }
}