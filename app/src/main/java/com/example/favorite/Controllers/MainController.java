package com.example.favorite.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favorite.Models.Place;
import com.example.favorite.Models.PlaceItem;
import com.example.favorite.R;
import java.util.ArrayList;
import java.util.List;

public class MainController extends AppCompatActivity {

    private ListView listView;
    private List<PlaceItem> placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlaceItem selectedPlace = placeList.get(i);
                Intent intent = new Intent(MainController.this, PlaceDetailController.class);
                intent.putExtra("placeName", selectedPlace.getName());
                intent.putExtra("description", selectedPlace.getDescription());
                intent.putExtra("latitude", String.valueOf(selectedPlace.getLatitude()));
                intent.putExtra("longitude", String.valueOf(selectedPlace.getLongitude()));
                startActivity(intent);
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
        placeList = Place.getAll(this);
        List<String> placeNames = new ArrayList<>();

        for (PlaceItem place : placeList) {
            placeNames.add(place.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, placeNames);
        listView.setAdapter(adapter);
    }
}
