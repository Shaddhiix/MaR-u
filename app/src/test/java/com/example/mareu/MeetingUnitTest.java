package com.example.mareu;

import com.example.mareu.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.services.MeetingApiService;
import com.example.mareu.services.MeetingGenerator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import android.graphics.Color;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
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
        service = DI.getNewInstanceApiService ();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetings = service.getMeeting ();
        List<Meeting> expectedMeetings = MeetingGenerator.DUMMY_MEETINGS;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void addMeetingWithSuccess() {
        long l= 2;
        String nameMeeting = "app";
        String nameRoom = "Salle 2";
        String dateMeeting = "25/06/2022";
        String timeMeeting = "17h00";
        String personList = "alex@lamzone.com, daniel@lamzone.com, yahweh@lamzone.com";
        int colorMeeting = Color.BLACK;
        Meeting meetingToAdd = new Meeting (l, nameMeeting, nameRoom,dateMeeting, timeMeeting,personList,colorMeeting);
        service.createMeeting(meetingToAdd);
        assertTrue(service.getMeeting().contains(meetingToAdd));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeeting().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeeting().contains(meetingToDelete));
    }

    @Test
    public void meetingByRoom() {
        List<Meeting> actualMeetingByRoom = service.getMeeting();
        Meeting meetingByRoom = actualMeetingByRoom.get(1);
        List<Meeting> expectedMeetingByRoom = service.getMeetingByRoom("Salle 1");
        assertThat(expectedMeetingByRoom.size(), is(1));
        assertTrue(expectedMeetingByRoom.contains(meetingByRoom));
    }

    @Test
    public void meetingByDate() {
        List<Meeting> actualMeetingByDate = service.getMeeting();
        Meeting meetingByDate = actualMeetingByDate.get(1);
        List<Meeting> expectedMeetingByDate = service.getMeetingByDate("27/07/2022");
        assertThat(expectedMeetingByDate.size(), is(1));
        assertTrue(expectedMeetingByDate.contains(meetingByDate));
    }
}