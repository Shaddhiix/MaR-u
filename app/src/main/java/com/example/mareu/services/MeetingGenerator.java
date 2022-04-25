package com.example.mareu.services;

import android.graphics.Color;

import com.example.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(
          new Meeting(System.currentTimeMillis(), "Présentation A", "Salle 1","15/04/2022",
                  "10:00","max@lamzone.com", Color.BLUE)
            );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}