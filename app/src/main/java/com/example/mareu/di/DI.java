package com.example.mareu.di;

import com.example.mareu.services.DummyMeetingApiService;
import com.example.mareu.services.MeetingApiService;

public class DI {

    public static MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    /**
     * Api service used for tests
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService ();
    }
}
