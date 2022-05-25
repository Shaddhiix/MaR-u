package com.example.mareu.dialog_box;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;
import com.example.mareu.databinding.DialogFilterDateBinding;
import com.example.mareu.events.FilterByDateEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDialog extends DialogFragment {

    private DialogFilterDateBinding dialogDate;
    private View dialogView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialogDate = DialogFilterDateBinding.inflate(LayoutInflater.from(getActivity()));
        dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_filter_date, null);
        return new AlertDialog.Builder(getActivity()).setView(dialogDate.getRoot()).setTitle("Choix de la Date").create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return dialogView;
    }

    @Override
    public void onDestroyView() {
        dialogView = null;
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialogDate.dialogDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialogDate.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
                    Date selectedDate = simpleDateFormat.parse(dialogDate.dialogDateEdit.getText()
                            .toString().trim());
                    EventBus.getDefault().post(new FilterByDateEvent(selectedDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });

        dialogDate.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
