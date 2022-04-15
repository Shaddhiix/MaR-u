package com.example.mareu.services;

import com.example.mareu.model.Meeting;

import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = MeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeeting() {
        return meetings;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }
}
