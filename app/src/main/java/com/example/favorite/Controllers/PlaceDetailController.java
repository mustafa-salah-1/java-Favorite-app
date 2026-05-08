package com.example.favorite.Controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favorite.Models.PlaceItem;
import com.example.favorite.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import com.example.favorite.Components.SavePlaceDialogFragment;
import com.example.favorite.Components.DeleteConfirmDialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PlaceDetailController extends AppCompatActivity  {
    private PlaceItem currentPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        TextView textViewName = findViewById(R.id.textViewDetailName);
        TextView textViewCoords = findViewById(R.id.textViewDetailCoords);
        TextView textViewDescription = findViewById(R.id.textViewDetailDescription);
        View cardViewDescription = findViewById(R.id.cardViewDescription);
        MapView mapView = findViewById(R.id.mapView);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.favorite", Context.MODE_PRIVATE);
        Configuration.getInstance().load(this, sharedPreferences);

        String name = getIntent().getStringExtra("placeName");
        String description = getIntent().getStringExtra("description");
        String latStr = getIntent().getStringExtra("latitude");
        String lonStr = getIntent().getStringExtra("longitude");

        if (name != null && latStr != null && lonStr != null) {
            double latitude = Double.parseDouble(latStr);
            double longitude = Double.parseDouble(lonStr);
            currentPlace = new PlaceItem(name, description, latitude, longitude);

            textViewName.setText(currentPlace.getName());
            textViewCoords.setText("Latitude: " + currentPlace.getLatitude() + " | Longitude: " + currentPlace.getLongitude());

            if (currentPlace.getDescription() != null && !currentPlace.getDescription().isEmpty()) {
                textViewDescription.setText(currentPlace.getDescription());
                cardViewDescription.setVisibility(View.VISIBLE);
            } else {
                cardViewDescription.setVisibility(View.GONE);
            }

            GeoPoint startPoint = new GeoPoint(latitude, longitude);

            mapView.setMultiTouchControls(true);
            mapView.getController().setZoom(15.0);
            mapView.getController().setCenter(startPoint);

            Marker marker = new Marker(mapView);
            marker.setPosition(startPoint);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            marker.setTitle(currentPlace.getName());
            mapView.getOverlays().add(marker);
        }

    }

    public void openMainPage(View view) {
        finish();
    }

    public void editPlace(View view) {
        SavePlaceDialogFragment dialog = SavePlaceDialogFragment.newInstance(
                    new GeoPoint(currentPlace.getLatitude(), currentPlace.getLongitude()),
                    currentPlace.getName(),
                    currentPlace.getDescription()
        );
        dialog.show(getSupportFragmentManager(), "EditPlaceDialog");
    }

    public void deletePlace(View view) {
        DeleteConfirmDialogFragment dialog = DeleteConfirmDialogFragment.newInstance(currentPlace);
        dialog.show(getSupportFragmentManager(), "DeleteConfirmDialog");
    }

}

