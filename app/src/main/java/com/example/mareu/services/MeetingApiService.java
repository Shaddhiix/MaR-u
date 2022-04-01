package com.example.mareu.services;

import com.example.mareu.model.Meeting;

public interface MeetingApiService {

    void createMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

}
