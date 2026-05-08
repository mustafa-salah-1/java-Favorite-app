package com.example.favorite.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favorite.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

import com.example.favorite.Components.SavePlaceDialogFragment;

public class AddPlaceController extends AppCompatActivity implements SavePlaceDialogFragment.SavePlaceListener {

    private MapView mapView;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Logic: Initialize osmdroid configuration before setting the content view
        Context ctx = getApplicationContext();
        sharedPreferences = getSharedPreferences("com.example.favorite", Context.MODE_PRIVATE);
        Configuration.getInstance().load(ctx, sharedPreferences);

        setContentView(R.layout.activity_add_place);

        mapView = findViewById(R.id.mapView);
        mapView.setMultiTouchControls(true);

        // Set a default center and zoom for adding mode
        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        // Default center (e.g., Erbil as per user's prompt example coords 36.19, 44.00)
        mapController.setCenter(new GeoPoint(36.1900, 44.0090));

        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                showSaveDialog(p);
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay OverlayEvents = new MapEventsOverlay(mReceive);
        mapView.getOverlays().add(OverlayEvents);

        // Check if we are viewing an existing place
        Intent intent = getIntent();
        String name = intent.getStringExtra("placeName");
        String latStr = intent.getStringExtra("latitude");
        String lonStr = intent.getStringExtra("longitude");

        if (name != null && latStr != null && lonStr != null) {
            try {
                double lat = Double.parseDouble(latStr);
                double lon = Double.parseDouble(lonStr);
                GeoPoint startPoint = new GeoPoint(lat, lon);
                mapController.setCenter(startPoint);

                Marker startMarker = new Marker(mapView);
                startMarker.setPosition(startPoint);
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                startMarker.setTitle(name);
                String desc = intent.getStringExtra("description");
                if (desc != null) {
                    startMarker.setSnippet(desc);
                }
                mapView.getOverlays().add(startMarker);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }

    private void showSaveDialog(final GeoPoint p) {
        SavePlaceDialogFragment dialog = SavePlaceDialogFragment.newInstance(p);
        dialog.show(getSupportFragmentManager(), "SavePlaceDialog");
    }

    @Override
    public void onPlaceSaved(String name, String description, GeoPoint p) {
        savePlace(name, description, p.getLatitude(), p.getLongitude());
    }

    private void savePlace(String name, String description, double lat, double lon) {
        // Retrieve existing places
        Set<String> set = sharedPreferences.getStringSet("places", new HashSet<String>());

        // Create a new set to ensure SharedPreferences detects the change
        Set<String> newSet = new HashSet<>(set);

        // Format: "PlaceName,Description,Latitude,Longitude"
        String placeString = name + "," + description + "," + lat + "," + lon;
        newSet.add(placeString);

        // Save back to SharedPreferences
        sharedPreferences.edit().putStringSet("places", newSet).apply();

        // Close MapActivity to return to MainActivity
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    public void openMainPage(View view) {
        finish();
    }
}
