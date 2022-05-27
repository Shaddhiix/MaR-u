package com.example.mareu;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.services.MeetingApiService;
import com.example.mareu.services.MeetingGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Unit test on Meeting service
 */
public class MeetingUnitTest {

private MeetingApiService service;

@Before
public void setUp() {
    service = DI.getNewInstanceApiService();
}
@Test
    public void getMeetingWithSuccess() {
    List<Meeting> meetings = service.getMeeting ();
    List<Meeting> expectedMeetings = MeetingGenerator.DUMMY_MEETINGS;
    assertThat();
}
}