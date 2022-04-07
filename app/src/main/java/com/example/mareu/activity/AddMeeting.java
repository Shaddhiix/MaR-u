package com.example.mareu.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mareu.databinding.ActivityMeetingAddBinding;

import java.util.Calendar;

public class AddMeeting extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public DatePickerDialog datePickerDialog;
    public TimePickerDialog timePickerDialog;
    Spinner spinRoom;
    ActivityMeetingAddBinding acBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMeetingAddBinding.inflate(getLayoutInflater());
        View view = acBinding.getRoot();
        setContentView(view);

        Calendar calendar = Calendar.getInstance();

        //DatePicker
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        acBinding.tvDateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        //TimePicker
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        acBinding.tvTimeMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        //Spinner
        spinRoom = acBinding.spinnerRoomMeeting;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month +=1;
        String date = dayOfMonth + "/" + month + "/" + year;
        acBinding.tvDateMeeting.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        String time = hourOfDay + ":" + minute;
        acBinding.tvTimeMeeting.setText(time);
    }
}