package com.example.favorite.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favorite.Models.Place;
import com.example.favorite.Models.PlaceItem;
import com.example.favorite.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;

import android.view.View;

import com.example.favorite.Components.SavePlaceDialogFragment;

public class AddPlaceController extends AppCompatActivity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.favorite", Context.MODE_PRIVATE);
        Configuration.getInstance().load(getApplicationContext(), sharedPreferences);

        setContentView(R.layout.activity_add_place);

        mapView = findViewById(R.id.mapView);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
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
        SavePlaceDialogFragment dialog = SavePlaceDialogFragment.newInstance(p);
        dialog.show(getSupportFragmentManager(), "SavePlaceDialog");
    }

    public void openMainPage(View view) {
        finish();
    }
}
