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

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

public class AddPlaceController extends AppCompatActivity {

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

    }

    private void showSaveDialog(final GeoPoint p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name of this place");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                if (!name.isEmpty()) {
                    savePlace(name, p.getLatitude(), p.getLongitude());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void savePlace(String name, double lat, double lon) {
        // Retrieve existing places
        Set<String> set = sharedPreferences.getStringSet("places", new HashSet<String>());

        // Create a new set to ensure SharedPreferences detects the change
        Set<String> newSet = new HashSet<>(set);

        // Format: "PlaceName,Latitude,Longitude"
        String placeString = name + "," + lat + "," + lon;
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
        Intent intent = new Intent(AddPlaceController.this, MainController.class);
        startActivity(intent);
        finish();
    }
}
