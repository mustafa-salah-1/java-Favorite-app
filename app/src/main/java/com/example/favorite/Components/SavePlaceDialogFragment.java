package com.example.favorite.Components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.favorite.Models.Place;
import com.example.favorite.Models.PlaceItem;
import com.example.favorite.R;
import com.google.android.material.textfield.TextInputEditText;

import org.osmdroid.util.GeoPoint;

public class SavePlaceDialogFragment extends DialogFragment {

    private GeoPoint point;

    public static SavePlaceDialogFragment newInstance(GeoPoint p) {
        return newInstance(p, "", "");
    }

    public static SavePlaceDialogFragment newInstance(GeoPoint p, String name, String description) {
        SavePlaceDialogFragment fragment = new SavePlaceDialogFragment();
        Bundle args = new Bundle();
        args.putDouble("lat", p.getLatitude());
        args.putDouble("lon", p.getLongitude());
        args.putString("name", name);
        args.putString("description", description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            double lat = getArguments().getDouble("lat");
            double lon = getArguments().getDouble("lon");
            point = new GeoPoint(lat, lon);
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_save_place, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Dialog dialog = getDialog();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);

        final TextInputEditText nameInput = view.findViewById(R.id.nameInput);
        final TextInputEditText descriptionInput = view.findViewById(R.id.descriptionInput);
        Button button_save = view.findViewById(R.id.button_save);
        Button button_cancel = view.findViewById(R.id.button_cancel);

        if (getArguments() != null) {
            String initialName = getArguments().getString("name", "");
            String initialDescription = getArguments().getString("description", "");
            nameInput.setText(initialName);
            descriptionInput.setText(initialDescription);
        }

        button_save.setOnClickListener(v -> {
            String name = nameInput.getText() != null ? nameInput.getText().toString().trim() : "";
            String description = descriptionInput.getText() != null ? descriptionInput.getText().toString() : "";
            
            if (name.trim().isEmpty()) {
                nameInput.setText(null);
                nameInput.setError("Please enter a name");
                return;
            }

            String initialName = getArguments() != null ? getArguments().getString("name", "") : "";
            
            // If it's a new place OR the name has changed, check for duplicates
            if ((initialName.isEmpty() || !name.equalsIgnoreCase(initialName)) && Place.isNameExists(getContext(), name)) {
                Toast.makeText(getContext(), "we have that name", Toast.LENGTH_SHORT).show();
                return;
            }

            PlaceItem newPlace = new PlaceItem(name, description, point.getLatitude(), point.getLongitude());
            
            if (initialName.isEmpty()) {
                Place.addPlace(getContext(), newPlace);
            } else {
                String initialDescription = getArguments().getString("description", "");
                PlaceItem oldPlace = new PlaceItem(initialName, initialDescription, point.getLatitude(), point.getLongitude());
                Place.updatePlace(getContext(), oldPlace, newPlace);
            }
            
            dismiss();
            getActivity().finish();
        });

        button_cancel.setOnClickListener(v -> dismiss());
    }
}
