package com.example.mareu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.databinding.ListMeetingBinding;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.services.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class MeetingList extends AppCompatActivity {

    private MeetingApiService apiService;
    private ListMeetingBinding lmBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lmBinding = ListMeetingBinding.inflate(getLayoutInflater());
        setContentView(lmBinding.getRoot());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lmBinding.recyclerView.setLayoutManager(layoutManager);

        apiService = DI.getMeetingApiService();

        lmBinding.floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingList.this,AddMeeting.class);
                startActivity(intent);
            }
        });

        initList();
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
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        apiService.deleteMeeting(event.meeting);
        initList();
    }
}