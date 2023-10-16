// AppointmentApp.java
// W. Geesey
// 10/5/2023
// Class to represent contacts

package edu.fscj.cop2805c.calendar;

import java.time.ZoneId;
import java.util.Locale;

// Class to create the contact object(s).
public class Contact {
    private StringBuilder name;
    private String email;
    private String phoneNumber;
    private ReminderPreference reminderPreference;
    private ZoneId timezone;
    private Locale locale;

    public Contact(String fName, String lName, String email, String phoneNumber,
                   ReminderPreference reminderPreference, ZoneId timezone, Locale locale) {
        this.name = new StringBuilder();
        this.name.append(fName).append(" ").append(lName);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.reminderPreference = reminderPreference;
        this.timezone = timezone;
        this.locale = locale;
    }

    public StringBuilder getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ReminderPreference getReminderPreference() {
        return reminderPreference;
    }

    public ZoneId getTimezone() {
        return timezone;
    }

    public Locale getLocale() {
        return locale;
    }

    // Formats the Contact information to be easy to read.
    @Override
    public String toString() {
        String cs = this.getName() + " | " + this.getEmail() + " | " + this.getPhoneNumber() + " | Reminder Preference: " +
                this.getReminderPreference() + " | TimeZone: " + this.getTimezone();
        return cs;
    }
}