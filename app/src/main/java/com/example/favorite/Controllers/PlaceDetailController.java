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

public class PlaceDetailController extends AppCompatActivity {

    private TextView textViewName, textViewCoords, textViewDescription;
    private View cardViewDescription;
    private Button buttonViewOnMap, buttonBack;
    private MapView mapView;
    private String name, description, lat, lon;

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
}

