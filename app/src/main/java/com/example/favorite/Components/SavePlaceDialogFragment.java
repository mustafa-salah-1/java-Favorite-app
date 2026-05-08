package com.example.favorite.Components;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.favorite.R;
import com.google.android.material.textfield.TextInputEditText;

import org.osmdroid.util.GeoPoint;

public class SavePlaceDialogFragment extends DialogFragment {

    public interface SavePlaceListener {
        void onPlaceSaved(String name, String description, GeoPoint p);
    }

    private SavePlaceListener listener;
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
        if (context instanceof SavePlaceListener) {
            listener = (SavePlaceListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SavePlaceListener");
        }
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

        final TextInputEditText nameInput = view.findViewById(R.id.nameInput);
        final TextInputEditText descriptionInput = view.findViewById(R.id.descriptionInput);
        Button btnSave = view.findViewById(R.id.button_save);
        Button btnCancel = view.findViewById(R.id.button_cancel);

        if (getArguments() != null) {
            String initialName = getArguments().getString("name", "");
            String initialDescription = getArguments().getString("description", "");
            nameInput.setText(initialName);
            descriptionInput.setText(initialDescription);
        }

        btnSave.setOnClickListener(v -> {
            String name = nameInput.getText() != null ? nameInput.getText().toString() : "";
            String description = descriptionInput.getText() != null ? descriptionInput.getText().toString() : "";
            if (!name.isEmpty()) {
                listener.onPlaceSaved(name, description, point);
                dismiss();
            } else {
                nameInput.setError("Please enter a name");
            }
        });

        btnCancel.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
