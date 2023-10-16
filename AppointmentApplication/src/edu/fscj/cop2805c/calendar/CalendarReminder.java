// AppointmentApp.java
// W. Geesey
// 10/5/2023
// Interface for reminders

package edu.fscj.cop2805c.calendar;

public interface CalendarReminder {

    // Build reminder in the form of a formatted String
    public Reminder buildReminder(Appointment appt);

    // Send reminder using contact's preferred notification method
    public void sendReminder(Reminder reminder);
}
