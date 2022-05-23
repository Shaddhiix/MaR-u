package com.example.mareu.dialog_box;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mareu.R;
import com.example.mareu.databinding.DialogRoomSpinnerBinding;
import com.example.mareu.events.FilterByRoomEvent;

import org.greenrobot.eventbus.EventBus;

public class RoomDialog extends AppCompatDialogFragment {

    private DialogRoomSpinnerBinding drsBinding;
    private View dialogview;

   @NonNull
    @Override
   public Dialog onCreateDialog(Bundle savedInstanceState) {
       drsBinding = DialogRoomSpinnerBinding.inflate(LayoutInflater.from(getActivity()));
       dialogview = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_room_spinner, null);
        return new AlertDialog.Builder(getActivity()).setView(drsBinding.getRoot()).setTitle("Choix de la salle").create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return dialogview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.room_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drsBinding.dialogRoomSpinner.setAdapter(adapter);

        drsBinding.dialogRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        drsBinding.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedRoom = drsBinding.dialogRoomSpinner.getSelectedItem().toString().trim();
                EventBus.getDefault().post(new FilterByRoomEvent(selectedRoom));
                dismiss();
            }
        });

        drsBinding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    @Override
    public void onDestroy() {
       dialogview = null;
        super.onDestroy();
    }
}
