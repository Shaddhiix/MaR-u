package com.example.mareu.services;

import android.graphics.Color;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
          new Meeting(System.currentTimeMillis(), "Step'n' App", "Salle 1","15/6/2022",
                  "10h00","max@lamzone.com", Color.BLUE),
            new Meeting(System.currentTimeMillis(), "Holy App", "Salle 5", "27/07/2022",
                    "10h00","Ezéchiel@lamzone.com, Job@lamzone.com, David@lamzone.com", Color.BLACK)
            );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}