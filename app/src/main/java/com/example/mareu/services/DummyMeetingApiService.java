package com.example.mareu.services;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private final List<Meeting> meetings = MeetingGenerator.generateMeetings();

    /**
     * Get a Meeting list
     */
    @Override
    public List<Meeting> getMeeting() {
        return meetings;
    }

    /**
     * Get a Meeting list filtered by Room
     */
    @Override
    public List<Meeting> getMeetingByRoom(String roomSelected) {
        List<Meeting> filterByRoom = new ArrayList<>();
        for (Meeting m : meetings) {
            if (m.getNameRoom().equals(roomSelected)) {
                filterByRoom.add(m);
            }
        }
        return filterByRoom;
    }

    /**
     * Get a Meeting list filtered by date
     */
    @Override
    public List<Meeting> getMeetingByDate(Date date) {
        List<Meeting> filterByDate = new ArrayList<>();
        for (Meeting m : meetings) {

        }
        return filterByDate;
    }

    /**
     * create a Meeting
     */
    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    /**
     * Delete a Meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }
}
