// AppointmentApp.java
// W. Geesey
// 10/5/2023
// Class to represent reminders

package edu.fscj.cop2805c.calendar;

import java.time.ZonedDateTime;

public class Reminder {
    private String text;
    private ZonedDateTime dateTime;
    private Contact contact;

    // Receives the information from the buildReminder method to build the reminder.
    public Reminder(String text, ZonedDateTime dateTime, Contact contact) {
        this.text = text;
        this.dateTime = dateTime;
        this.contact = contact;
    }

    public String getText() {
        return text;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public Contact getContact() {
        return contact;
    }

    // Override to print out the text (the reminder message).
    @Override
    public String toString() {
//        String reminderText = "";
//        reminderText += getText();
        return getText();
    }
}