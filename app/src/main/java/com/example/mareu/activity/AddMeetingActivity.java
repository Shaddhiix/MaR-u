package com.example.mareu.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.mareu.R;
import com.example.mareu.databinding.ActivityMeetingAddBinding;
import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.services.MeetingApiService;

import java.util.Random;

public class AddMeetingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Spinner spinRoom;
    private ActivityMeetingAddBinding acBinding;
    private MeetingApiService meetingApiService;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        acBinding = ActivityMeetingAddBinding.inflate(getLayoutInflater());
        setContentView(acBinding.getRoot());

        //Press Back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        acBinding.tvDateMeeting.setOnClickListener(v -> {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

        acBinding.tvTimeMeeting.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(),"time picker");
        });

        //Spinner
        spinRoom = acBinding.spinnerRoomMeeting;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.room_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRoom.setAdapter(adapter);

        meetingApiService = DI.getMeetingApiService();

        randomColor();
        addMeeting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month +=1;
        String date = dayOfMonth + "/" + month + "/" + year;
        acBinding.tvDateMeeting.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String time = hourOfDay + "h" + minute;
        acBinding.tvTimeMeeting.setText(time);
    }

    void addMeeting() {
        acBinding.btnCreateMeeting.setOnClickListener(v -> {
            Meeting meeting = new Meeting(
                    System.currentTimeMillis(),
                    acBinding.spinnerRoomMeeting.getSelectedItem().toString(),
                    acBinding.tvNameMeeting.getText().toString(),
                    acBinding.tvDateMeeting.getText().toString(),
                    acBinding.tvTimeMeeting.getText().toString(),
                    acBinding.tvPersonName.getText().toString(),
                    color);

            meetingApiService.createMeeting(meeting);
            finish();
        });
    }

    void randomColor() {
        Random rnd = new Random();
        color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        Drawable view = acBinding.avatarRoom.getBackground();
        view.setTint(color);
    }
}