package com.example.mareu.events;

import java.util.Date;

public class FilterByDateEvent {
    Date dateSelected;

    public FilterByDateEvent(Date dateSelected) {
        this.dateSelected = dateSelected;
    }

    public Date getDateSelected() {
        return dateSelected;
    }
}
