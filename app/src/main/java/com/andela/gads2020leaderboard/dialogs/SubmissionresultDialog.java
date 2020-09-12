package com.andela.gads2020leaderboard.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.andela.gads2020leaderboard.R;

public class SubmissionresultDialog extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getContext() == null) return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = View.inflate(getContext(), R.layout.dialog_submission_result, null);
        int rsImage = (getArguments() != null) ? getArguments().getInt("result_image") : 0;
        ImageView resultImage = (ImageView) view.findViewById(R.id.result_image);
        if (rsImage == 1) {
            resultImage.setImageResource(R.drawable.img_successfull);
        } else {
            resultImage.setImageResource(R.drawable.img_not_successfull);
        }
        String rsMessage = (getArguments() != null) ? getArguments().getString("result_message") : "";
        TextView resultMessage = (TextView) view.findViewById(R.id.result_message);
        resultMessage.setText(rsMessage);
        builder.setView(view);
        return builder.create();
    }
}
