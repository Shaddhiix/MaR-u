package com.example.mareu.services;

import com.example.mareu.model.Meeting;

import java.util.Date;
import java.util.List;

public interface MeetingApiService {

    /**
     * Get a Meeting list
     */
    List<Meeting> getMeeting();

    /**
     * Get a Meeting list filtered by Room
     */
    List<Meeting> getMeetingByRoom(String roomSelected);

    /**
     * Get a Meeting list filtered by Date
     */
    List<Meeting> getMeetingByDate(String dateSelected);

    /**
     * create a Meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Delete a Meeting
     */
    void deleteMeeting(Meeting meeting);

}
