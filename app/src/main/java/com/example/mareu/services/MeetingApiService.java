package com.example.mareu.services;

import com.example.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeeting();

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

}
