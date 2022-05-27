package com.example.mareu.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.databinding.ListMeetingBinding;
import com.example.mareu.di.DI;
import com.example.mareu.dialog_box.RoomDialog;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.events.FilterByRoomEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.services.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MeetingListActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private MeetingApiService apiService;
    private ListMeetingBinding lmBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lmBinding = ListMeetingBinding.inflate(getLayoutInflater());
        setContentView(lmBinding.getRoot());
        apiService = DI.getMeetingApiService();

        initRecyclerView();
        initFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.by_all:
                initList();
                break;
            case R.id.by_room:
                RoomDialog roomDialog = new RoomDialog();
                roomDialog.show(getSupportFragmentManager(), "room dialog box");
                break;
            case R.id.by_date:
                DialogFragment datePicker = new DatePickerFragment ();
                datePicker.show ( getSupportFragmentManager (), "date picker" );
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFab() {
        lmBinding.floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingListActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lmBinding.recyclerView.setLayoutManager(layoutManager);
    }

    private void initList() {
        List<Meeting> lMeetings = apiService.getMeeting();
        lmBinding.recyclerView.setAdapter(new MeetingRecyclerViewAdapter(lMeetings));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        apiService.deleteMeeting(event.meeting);
        initList();
    }

    @Subscribe
    public void onFilterByRoom(FilterByRoomEvent event) {
        List<Meeting> lMeetings = apiService.getMeetingByRoom(event.getRoomSelected());
        lmBinding.recyclerView.setAdapter(new MeetingRecyclerViewAdapter(lMeetings));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month += 1;
        String date = dayOfMonth + "/" + month + "/" + year;
        Log.d("tagii", "date" + date);
        List<Meeting> lMeetings = apiService.getMeetingByDate(date);
        lmBinding.recyclerView.setAdapter((new MeetingRecyclerViewAdapter(lMeetings)));
    }
}