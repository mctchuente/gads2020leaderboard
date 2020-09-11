package com.andela.gads2020leaderboard.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.andela.gads2020leaderboard.R;

public class ConfirmationDialog extends DialogFragment {
    public interface ISubmissionConfirmationDialogListener {
        void onConfirmationYesClick(boolean yes);
    }
    public ISubmissionConfirmationDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getActivity() instanceof ISubmissionConfirmationDialogListener) {
            listener = (ISubmissionConfirmationDialogListener) getActivity();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getContext() == null) return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_confirmation, null);
        ImageButton btnCancel = (ImageButton) view.findViewById(R.id.dialog_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog d = (AlertDialog) getDialog();
                if (d != null) d.dismiss();
            }
        });
        Button btnYes = (Button) view.findViewById(R.id.dialog_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog d = (AlertDialog) getDialog();
                if (d != null && listener != null) {
                    listener.onConfirmationYesClick(true);
                    d.dismiss();
                }
            }
        });
        builder.setView(view);
        setCancelable(false);
        return builder.create();
    }
}
