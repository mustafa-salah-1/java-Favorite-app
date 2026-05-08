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

public class DeleteConfirmDialogFragment extends DialogFragment {
    private static PlaceItem p;

    public static DeleteConfirmDialogFragment newInstance(PlaceItem currentPlace) {
        p = currentPlace;
        return new DeleteConfirmDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return inflater.inflate(R.layout.dialog_confirm_delete, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dialog dialog = getDialog();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setLayout(width, height);

        Button btnDelete = view.findViewById(R.id.btnDelete);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnDelete.setOnClickListener(v -> {
            Place.deletePlace(getActivity(), p);
            Toast.makeText(getActivity(), "Place deleted", Toast.LENGTH_SHORT).show();
            dismiss();
            getActivity().finish();
        });

        btnCancel.setOnClickListener(v -> dismiss());
    }
}
