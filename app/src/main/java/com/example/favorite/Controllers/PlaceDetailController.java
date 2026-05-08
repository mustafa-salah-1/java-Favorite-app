package com.example.favorite.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favorite.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import com.example.favorite.Components.SavePlaceDialogFragment;
import com.example.favorite.Components.DeleteConfirmDialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;
import android.widget.Toast;

public class PlaceDetailController extends AppCompatActivity implements 
        SavePlaceDialogFragment.SavePlaceListener, 
        DeleteConfirmDialogFragment.DeleteConfirmListener {

    private TextView textViewName, textViewCoords, textViewDescription;
    private View cardViewDescription;
    private Button buttonViewOnMap, buttonBack;
    private MapView mapView;
    private String name, description, lat, lon;
    private SharedPreferences sharedPreferences;
    private String originalPlaceString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        textViewName = findViewById(R.id.textViewDetailName);
        textViewCoords = findViewById(R.id.textViewDetailCoords);
        textViewDescription = findViewById(R.id.textViewDetailDescription);
        cardViewDescription = findViewById(R.id.cardViewDescription);
        mapView = findViewById(R.id.mapView);

        // OsmDroid Configuration
        Configuration.getInstance().load(this, android.preference.PreferenceManager.getDefaultSharedPreferences(this));

        name = getIntent().getStringExtra("placeName");
        description = getIntent().getStringExtra("description");
        lat = getIntent().getStringExtra("latitude");
        lon = getIntent().getStringExtra("longitude");

        sharedPreferences = getSharedPreferences("com.example.favorite", Context.MODE_PRIVATE);
        // Construct the original string to find it later for editing/deletion
        originalPlaceString = name + "," + (description != null ? description : "") + "," + lat + "," + lon;

        textViewName.setText(name);
        textViewCoords.setText("Latitude: " + lat + " | Longitude: " + lon);

        if (description != null && !description.isEmpty()) {
            textViewDescription.setText(description);
            cardViewDescription.setVisibility(View.VISIBLE);
        } else {
            cardViewDescription.setVisibility(View.GONE);
        }

        // Initialize Map
        if (lat != null && lon != null) {
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lon);
            GeoPoint startPoint = new GeoPoint(latitude, longitude);

            mapView.setMultiTouchControls(true);
            mapView.getController().setZoom(15.0);
            mapView.getController().setCenter(startPoint);

            Marker marker = new Marker(mapView);
            marker.setPosition(startPoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle(name);
            mapView.getOverlays().add(marker);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }
    public void openMainPage(View view) {
        finish();
    }

    public void editPlace(View view) {
        if (lat != null && lon != null) {
            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lon);
            SavePlaceDialogFragment dialog = SavePlaceDialogFragment.newInstance(
                    new GeoPoint(latitude, longitude),
                    name,
                    description != null ? description : ""
            );
            dialog.show(getSupportFragmentManager(), "EditPlaceDialog");
        }
    }

    public void deletePlace(View view) {
        DeleteConfirmDialogFragment dialog = DeleteConfirmDialogFragment.newInstance();
        dialog.show(getSupportFragmentManager(), "DeleteConfirmDialog");
    }

    @Override
    public void onDeleteConfirmed() {
        removePlaceFromStorage();
        finish();
    }

    private void removePlaceFromStorage() {
        Set<String> set = sharedPreferences.getStringSet("places", new HashSet<String>());
        Set<String> newSet = new HashSet<>(set);
        
        // Try to find the string. Since it might have been saved in different formats, 
        // we should be careful. But based on AddPlaceController, it's name,description,lat,lon.
        if (newSet.contains(originalPlaceString)) {
            newSet.remove(originalPlaceString);
        } else {
            // Fallback: try to find by name and coords if description matches empty
            String fallback = name + ",," + lat + "," + lon;
            newSet.remove(fallback);
        }
        
        sharedPreferences.edit().putStringSet("places", newSet).apply();
        Toast.makeText(this, "Place deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlaceSaved(String newName, String newDescription, GeoPoint p) {
        // Remove old entry
        Set<String> set = sharedPreferences.getStringSet("places", new HashSet<String>());
        Set<String> newSet = new HashSet<>(set);
        newSet.remove(originalPlaceString);
        
        // Add new entry
        String newPlaceString = newName + "," + newDescription + "," + p.getLatitude() + "," + p.getLongitude();
        newSet.add(newPlaceString);
        
        sharedPreferences.edit().putStringSet("places", newSet).apply();
        
        // Update UI
        name = newName;
        description = newDescription;
        textViewName.setText(name);
        if (description != null && !description.isEmpty()) {
            textViewDescription.setText(description);
            cardViewDescription.setVisibility(View.VISIBLE);
        } else {
            cardViewDescription.setVisibility(View.GONE);
        }
        
        // Update originalPlaceString for subsequent edits
        originalPlaceString = newPlaceString;
        
        Toast.makeText(this, "Place updated", Toast.LENGTH_SHORT).show();
    }
}

