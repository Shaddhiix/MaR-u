package com.example.mareu.activity;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
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

        holder.itemBinding.tvMeetingName.setText(meeting.getNameMeeting());
        holder.itemBinding.tvTimeMeeting.setText(meeting.getTimeMeeting());
        holder.itemBinding.tvRoomMeeting.setText(meeting.getNameRoom());
        holder.itemBinding.tvEmployees.setText(meeting.getPersonList());

        Drawable background = holder.itemBinding.ivAvatar.getBackground();
        background.setTint(meeting.getColorMeeting());

        holder.itemBinding.ivDelete.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));

    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemMeetingBinding itemBinding;

        public ViewHolder(ItemMeetingBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}