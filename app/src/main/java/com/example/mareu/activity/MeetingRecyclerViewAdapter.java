package com.example.mareu.activity;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.databinding.ItemMeetingBinding;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {
    public List<Meeting> meetingList;

    public MeetingRecyclerViewAdapter(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMeetingBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Meeting meeting = meetingList.get(position);

        holder.viewBinding.tvRoomName.setText(meeting.getNameMeeting());

        Drawable background = holder.viewBinding.ivAvatar.getBackground();
        background.setTint(meeting.getColorMeeting());

        holder.viewBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemMeetingBinding viewBinding;

        public ViewHolder(ItemMeetingBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }
    }
}