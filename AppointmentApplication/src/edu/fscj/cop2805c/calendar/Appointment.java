// AppointmentApp.java
// W. Geesey
// 10/5/2023
// Class to represent appointments

package edu.fscj.cop2805c.calendar;

import java.time.ZonedDateTime;



// Class to create the appointment object(s).
public class Appointment {
    private String title, description;
    private Contact contact;
    private ZonedDateTime appointmentTime, reminderTime;
    public Appointment(String title, String description, Contact contact,
                       ZonedDateTime appointmentTime, ZonedDateTime reminderTime) {
        this.title = title;
        this.description = description;
        this.contact = contact;
        this.appointmentTime = appointmentTime;
        this.reminderTime = reminderTime;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Contact getContact() {
        return contact;
    }

    public ZonedDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public ZonedDateTime getReminderTime() {
        return reminderTime;
    }
}