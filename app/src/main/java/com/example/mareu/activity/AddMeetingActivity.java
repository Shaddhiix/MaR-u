package com.example.mareu.activity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;
import com.example.mareu.databinding.ActivityMeetingAddBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.picker.Pick;
import com.example.mareu.services.MeetingApiService;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddMeetingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private Spinner spinRoom;
    private ActivityMeetingAddBinding acBinding;
    private MeetingApiService meetingApiService;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        acBinding = ActivityMeetingAddBinding.inflate ( getLayoutInflater () );
        setContentView ( acBinding.getRoot () );
        meetingApiService = DI.getMeetingApiService ();

        //Press Back
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );

        //Date & time Picker
        initListener ();

        //Spinner
        initSpinner ();

        //Chip
        initChip ();

        randomColor ();
        addMeeting ();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home: {
                finish ();
                return true;
            }
        }
        return super.onOptionsItemSelected ( item );
    }
    
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String time = hourOfDay + "h" + minute;
        acBinding.tvTimeMeeting.setText ( time );
    }

    private void addMeeting() {
        acBinding.btnCreateMeeting.setOnClickListener(v -> {
            
            boolean isValid = true;
            if (acBinding.tvNameMeeting.length() < 3)
            {
                acBinding.tvNameMeeting.setError("Entrer un titre (3 caractère minimum)");
                acBinding.tvNameMeeting.requestFocus();
                isValid = false;
            }
            if (acBinding.tvDateMeeting.length() == 0)
            {
                acBinding.tvDateMeeting.setError("Sélectionner une date");
                acBinding.tvDateMeeting.requestFocus();
                isValid = false;
            }
            if (acBinding.tvTimeMeeting.length() == 0)
            {
                acBinding.tvTimeMeeting.setError("Sélectionner une heure");
                acBinding.tvTimeMeeting.requestFocus();
                isValid = false;
            }
            else {
                if(isValid) {
                    Meeting meeting = new Meeting (
                            System.currentTimeMillis (),
                            acBinding.tvNameMeeting.getText ().toString (),
                            acBinding.spinnerRoomMeeting.getSelectedItem ().toString (),
                            acBinding.tvDateMeeting.getText ().toString (),
                            acBinding.tvTimeMeeting.getText ().toString (),
                            getChipGroupValues (),
                            color );

                    meetingApiService.createMeeting ( meeting );
                    Toast.makeText ( this, "Réunion enregistrée", Toast.LENGTH_SHORT ).show ();
                    finish ();
                }
            }
        } );
    }

    private void randomColor() {
        Random rnd = new Random ();
        color = Color.argb ( 255, rnd.nextInt ( 256 ), rnd.nextInt ( 256 ), rnd.nextInt ( 256 ) );
        Drawable view = acBinding.avatarRoom.getBackground ();
        view.setTint ( color );
    }

    private void initListener() {

        acBinding.tvDateMeeting.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Pick.pickDate ( acBinding.tvDateMeeting, AddMeetingActivity.this);
            }
        } );

        acBinding.tvTimeMeeting.setOnClickListener ( v -> {
            DialogFragment timePicker = new TimePickerFragment ();
            timePicker.show ( getSupportFragmentManager (), "time picker" );
        } );
    }

    private void initSpinner() {

        spinRoom = acBinding.spinnerRoomMeeting;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource ( this,
                R.array.room_array, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource ( android.R.layout.simple_spinner_dropdown_item );
        spinRoom.setAdapter ( adapter );
    }

    private void initChip() {

        acBinding.tvPersonName.setOnEditorActionListener ( (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String email = acBinding.tvPersonName.getText ().toString ();
                if (!email.isEmpty ()) {
                    addChipGroup ( email );
                    acBinding.tvPersonName.setText ( "" );
                }
            }
            return true;
        } );
    }

    private void addChipGroup(String text) {
        Chip chip = new Chip ( this );
        chip.setText ( text );
        chip.setCloseIconVisible ( true );
        chip.setOnCloseIconClickListener ( v -> acBinding.chipGroupMail.removeView ( chip ) );

        chip.setClickable ( false );
        chip.setCheckable ( false );
        acBinding.chipGroupMail.addView ( chip );
    }

    private String getChipGroupValues() {
        ChipGroup chipGroup = acBinding.chipGroupMail;
        List<String> participants = new ArrayList<> ();
        for (int i = 0; i < chipGroup.getChildCount (); i++) {
            Chip chip = (Chip) chipGroup.getChildAt ( i );
            participants.add ( chip.getText ().toString () );
        }
        return participants.toString ().replace ( "[", "" ).replace ( "]", "" );
    }
}