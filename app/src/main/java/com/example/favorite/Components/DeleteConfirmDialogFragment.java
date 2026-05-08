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
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DeleteConfirmListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return inflater.inflate(R.layout.dialog_confirm_delete, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
