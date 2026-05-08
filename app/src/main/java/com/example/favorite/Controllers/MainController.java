package com.example.favorite.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favorite.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainController extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fab;
    private ArrayList<String> placeNames;
    private ArrayList<String> rawPlaces;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);


        sharedPreferences = getSharedPreferences("com.example.favorite", Context.MODE_PRIVATE);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Extract Name, Latitude, and Longitude from the saved string
                String rawPlace = rawPlaces.get(i);
                String[] parts = rawPlace.split(",");
                if (parts.length >= 3) {
                    Intent intent = new Intent(MainController.this, AddPlaceController.class);
                    intent.putExtra("placeName", parts[0]);
                    intent.putExtra("latitude", parts[1]);
                    intent.putExtra("longitude", parts[2]);
                    startActivity(intent);
                }
            }
        });
    }

    public void openPlaceSaver(View view) {
        Intent intent = new Intent(MainController.this, AddPlaceController.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        // Load the saved strings from SharedPreferences
        Set<String> set = sharedPreferences.getStringSet("places", new HashSet<String>());
        rawPlaces = new ArrayList<>(set);
        placeNames = new ArrayList<>();

        // Extract just the "PlaceName" for display
        for (String place : rawPlaces) {
            String[] parts = place.split(",");
            if (parts.length > 0) {
                placeNames.add(parts[0]);
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placeNames);
        listView.setAdapter(adapter);
    }
}
