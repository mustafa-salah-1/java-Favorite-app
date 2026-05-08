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

public class DeleteConfirmDialogFragment extends DialogFragment {

    public interface DeleteConfirmListener {
        void onDeleteConfirmed();
    }

    private DeleteConfirmListener listener;

    public static DeleteConfirmDialogFragment newInstance() {
        return new DeleteConfirmDialogFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteConfirmListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement DeleteConfirmListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return inflater.inflate(R.layout.dialog_confirm_delete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnDelete = view.findViewById(R.id.btnDelete);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnDelete.setOnClickListener(v -> {
            listener.onDeleteConfirmed();
            dismiss();
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
