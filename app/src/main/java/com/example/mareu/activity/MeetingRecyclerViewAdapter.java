package com.example.mareu.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.databinding.ItemMeetingBinding;
import com.example.mareu.model.Meeting;

import java.util.List;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {
    public List<Meeting> meetingList;

    public MeetingRecyclerViewAdapter(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMeetingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = meetingList.get(position);
        //holder.viewBinding.tvRoomName.setText(meeting.getFullName());
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMeetingBinding viewBinding;

        public ViewHolder(ItemMeetingBinding viewBinding) {
            super(viewBinding.getRoot());
            this.viewBinding = viewBinding;
        }
    }
}