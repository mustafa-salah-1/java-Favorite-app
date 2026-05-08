package com.example.favorite.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.favorite.Components.PlaceAdapter;
import com.example.favorite.Models.Place;
import com.example.favorite.Models.PlaceItem;
import com.example.favorite.R;
import java.util.ArrayList;
import java.util.List;

public class MainController extends AppCompatActivity {

    private ListView listView;
    private List<PlaceItem> displayList;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        search = findViewById(R.id.search_place);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlaceItem selectedPlace = displayList.get(i);
                Intent intent = new Intent(MainController.this, PlaceDetailController.class);
                intent.putExtra("placeName", selectedPlace.getName());
                intent.putExtra("description", selectedPlace.getDescription());
                intent.putExtra("latitude", String.valueOf(selectedPlace.getLatitude()));
                intent.putExtra("longitude", String.valueOf(selectedPlace.getLongitude()));
                startActivity(intent);
            }
        });

        search.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void openPlaceSaver(View view) {
        Intent intent = new Intent(MainController.this, AddPlaceController.class);
        startActivity(intent);
    }

    public void openAboutPage(View view) {
        Intent intent = new Intent(MainController.this, AboutController.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        filter(search.getText().toString());
    }

    private void filter(String text) {
        List<PlaceItem> allPlaces = Place.getAll(this);

        if (allPlaces == null) return;

        displayList = new ArrayList<>();
        List<String> placeNames = new ArrayList<>();
        for (PlaceItem item : allPlaces) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                displayList.add(item);
                placeNames.add(item.getName());
            }
        }
        PlaceAdapter adapter = new PlaceAdapter(this, displayList);
        listView.setAdapter(adapter);

        TextView countText = findViewById(R.id.number_of_social);
        countText.setText(displayList.size() + " Places");
    }

}
