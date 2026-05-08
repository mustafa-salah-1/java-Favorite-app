package com.example.favorite.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favorite.R;

public class PlaceDetailController extends AppCompatActivity {

    private TextView textViewName, textViewCoords, textViewDescription;
    private View cardViewDescription;
    private Button buttonViewOnMap, buttonBack;
    private String name, description, lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        textViewName = findViewById(R.id.textViewDetailName);
        textViewCoords = findViewById(R.id.textViewDetailCoords);
        textViewDescription = findViewById(R.id.textViewDetailDescription);
        cardViewDescription = findViewById(R.id.cardViewDescription);
        buttonViewOnMap = findViewById(R.id.buttonViewOnMap);
        buttonBack = findViewById(R.id.buttonBack);

        name = getIntent().getStringExtra("placeName");
        description = getIntent().getStringExtra("description");
        lat = getIntent().getStringExtra("latitude");
        lon = getIntent().getStringExtra("longitude");

        textViewName.setText(name);
        textViewCoords.setText("Lat: " + lat + "\nLon: " + lon);

        if (description != null && !description.isEmpty()) {
            textViewDescription.setText(description);
            cardViewDescription.setVisibility(View.VISIBLE);
        }

        buttonViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceDetailController.this, AddPlaceController.class);
                intent.putExtra("placeName", name);
                intent.putExtra("description", description);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                startActivity(intent);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
