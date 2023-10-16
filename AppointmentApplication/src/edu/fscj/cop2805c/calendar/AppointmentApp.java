// AppointmentApp.java
// W. Geesey
// 10/5/2023
// Produces appointment reminders

package edu.fscj.cop2805c.calendar;

import edu.fscj.cop2805c.dispatch.Dispatcher;

import java.util.*;
import java.time.*;
import java.time.format.*;
import java.util.stream.Stream;

//Main class, creates contact(s), appointments and reminders as well as dispatches
//reminders.
public class AppointmentApp implements CalendarReminder, Dispatcher<Reminder> {
    // ArrayList to hold the appointments
    private final ArrayList<Appointment> appointmentsList = new ArrayList<>();

    // ArrayLists to hold the appointment Title and Descriptions.
    private static final ArrayList<String> appointmentTitle = new ArrayList<>(Arrays.asList(
            "Therapist Appointment", "Dentist Appointment", "Mechanic Appointment",
            "Primary Care Appointment"));
    private static final ArrayList<String> appointmentDesc = new ArrayList<>(Arrays.asList(
            "Discuss sleeping issues", "Wisdom tooth extraction", "Oil change and tire rotation",
            "Annual Exam"));

    private Queue<Reminder> queue = new LinkedList<Reminder>();
    private Stream<Reminder> stream = queue.stream();

    public AppointmentApp() {
        // Constructor for initialization
    }
    //Dispatch method for adding the reminder to the queue and dispatching the reminder.
    public void dispatch(Reminder rem) {
        this.queue.add(rem);
    }

    //Send the reminders via Lambda implementation of the dispatch method.
    public void sendReminder(Reminder rem) {
        // Logic for the "sending a reminder ..." messages; find the contact preference then retrieve
        // the appropriate information, if no preference, do the else statement.
        var remPref = (rem.getContact().getReminderPreference() == ReminderPreference.EMAIL)
                ? rem.getContact().getEmail()
                : rem.getContact().getPhoneNumber();
        if (rem.getContact().getReminderPreference() != ReminderPreference.NONE) {
            System.out.println("Sending the following " + rem.getContact().getReminderPreference() +
                    " message to " + rem.getContact().getName() + " at " + remPref + ".");
        } else {
            System.out.println("Printing a reminder for " + rem.getContact().getName() + ".");
        }
        //dispatch(rem);
        Dispatcher<Reminder> d = (r) -> this.queue.add(r);
        d.dispatch(rem);
    }

    // Method implementation: Takes an appointment (appt) and builds a formatted reminder.
    public Reminder buildReminder(Appointment appt) {
        String msg =  "";
        // Try with resources to attempt to use the resource bundle to add localization, if not then
        // we use a standard message (catch).
        try {
            // Add localization to the message.
            // Create localized greeting.
            Contact c = appt.getContact();
            //System.out.println(appt.getContact().getLocale());   //Used to debug a nullPointer I was getting.
            ResourceBundle res = ResourceBundle.getBundle("edu.fscj.cop2805c.calendar.Calendar",
                    c.getLocale());
            String haveAppt = res.getString("haveAppt");
            String hello = res.getString("hello");

            // Format the localized date/time
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.FULL)
                            .localizedBy(c.getLocale());

            // This is the reminder message with localized formatting.
            msg = hello + " " + appt.getContact().getName() + "!\n" + haveAppt + "!\n" +
                    "Title: " + appt.getTitle() + "\n" + "Description: " + appt.getDescription() + "\n" +
                    "Date|Time: " + appt.getAppointmentTime().format(formatter) +
                    "\n";
        } catch (java.util.MissingResourceException e) {
            System.err.println(e);
            msg = "Hello, " + appt.getContact().getName() + "!\n" +
                  "This is a reminder that you have an upcoming appointment. \n" +
                  "Title: " + appt.getTitle() + "\n" + "Description: " + appt.getDescription() + "\n" +
                  "Date|Time: " + appt.getAppointmentTime().format(DateTimeFormatter
                    .ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.FULL)) + "\n";
        }
        // Copied from the Practice Assignment - variable to serve as delimiter to split the reminder.
        final String NEWLINE = "\n";
        // Split the message into separate lines
        String[] splitStr = msg.split(NEWLINE);

        // Find the longest line length
        int longest = getLongest(splitStr);

        // Create a StringBuilder for the card
        StringBuilder card = new StringBuilder();

        // Top border
        card.append("+").append("+".repeat(longest + 2)).append("+").append("\n");

        // Adding the content of the actual reminder message line by, appending to the StringBuilder.
        for (String line : splitStr) {
            card.append("+ ").append(line).append(" ".repeat(longest - line.length())).append(" +\n");
        }

        // Bottom border
        card.append("+").append("+".repeat(longest + 2)).append("+").append("\n");
        Reminder reminder = new Reminder(card.toString(), appt.getReminderTime(), appt.getContact());
        return reminder;
    }

    // Find the longest line length in an array of Strings (lines).
    public int getLongest(String[] lines) {
        int maxLength = 0;
        for (String line : lines) {
            if (line.length() > maxLength) {
                maxLength = line.length() + 1;
            }
        }
        return maxLength;
    }

    // Method to iterate over the appointments and send the appointment that matches the
    // current time to have a reminder built.
    // Currently setup to send each appointment to have a reminder built to
    // demonstrate localization.
    public void checkAndSendReminders() {
        //ZonedDateTime currentDateTime = ZonedDateTime.now();
        for (Appointment appointment : appointmentsList) {
            //ZonedDateTime reminderTime = appointment.getReminderTime();
            //if (currentDateTime.equals(reminderTime)) {
                Reminder rem = buildReminder(appointment);
                sendReminder(rem);
            //}
        }
    }

    // Method to add variable number of Appointment objects to the appointmentsList
    public void addAppointments(Appointment... appointments) {
        for (Appointment appointment : appointments) {
            appointmentsList.add(appointment);
        }
    }

    public static void main(String[] args) {

        AppointmentApp appointmentApp = new AppointmentApp();

        // Random object to create random appointment and reminder times
        Random random = new Random();

         //Create a contact - used when
//        Contact contact = new Contact("Donald", "Trump",
//                "E-Mail: d.trump@trump.com", "Phone: (123)-456-7890",
//                ReminderPreference.EMAIL, ZoneId.of("America/New_York"), new Locale("en"));
//
//
        // Generate random values for appointment and reminder times. Will make 4 appointments for
        // the appointmentsList arrayList.
//        for (int i = 0; i < 4; i++) {
//            int randomMonths = random.nextInt(12);
//            int randomHours = random.nextInt(24);
//            int apptIndex = random.nextInt(appointmentTitle.size());
//
//            // Generate random appointment and reminder times using LocalDateTime.now() as starting point.
//            LocalDateTime currentDateTime = LocalDateTime.now();
//            ZonedDateTime appointmentTime = ZonedDateTime.of(
//                    currentDateTime.plusMonths(randomMonths).plusHours(randomHours),
//                    contact.getTimezone());
//            ZonedDateTime reminderTime = appointmentTime.minusHours(randomHours);
//
//
//            // Get a random appointment title and matching description from the ArrayLists.
//            String title = appointmentTitle.get(apptIndex);
//            String description = appointmentDesc.get(apptIndex);
//
//            // Create the new appointment and add to the appropriate ArrayList.
//            Appointment appointment = new Appointment(title, description, contact, appointmentTime,
//                    reminderTime);
//            appointmentApp.addAppointments(appointment);
//        }

        //Create test Contacts and Appointments
        Contact contact1 = new Contact("Joe", "Biden",
                "E-Mail: j.biden@biden.com", "Phone: (123)-456-7890",
                ReminderPreference.NONE, ZoneId.of("America/New_York"), new Locale("en"));

        Contact contact2 = new Contact("Napoleon", "Bonaparte",
                "E-Mail: n.bonaparte@bonaparte.com", "Phone: (123)-456-7891",
                ReminderPreference.EMAIL, ZoneId.of("Europe/Paris"), new Locale("fr"));

        Contact contact3 = new Contact("Salvador", "Dalí",
                "E-Mail: s.dalí@dalí.com", "Phone: (123)-456-7892",
                ReminderPreference.TEXT, ZoneId.of("Europe/Madrid"), new Locale("es"));

        Contact contact4 = new Contact("Qín", "Shǐhuáng",
                "E-Mail: q.shǐhuáng@shǐhuáng.com", "Phone: (123)-456-7892",
                ReminderPreference.EMAIL, ZoneId.of("Asia/Chongqing"), new Locale("zh"));

        // Contact 5 is to test which locale would be used if unknown locale was used.
        Contact contact5 = new Contact("Joe", "Shmoe",
                "E-Mail: j.shmoe@shmoe.com", "Phone: (123)-456-0000",
                ReminderPreference.EMAIL, ZoneId.of("Asia/Chongqing"), new Locale("zq"));

        // Add the appointments to the arrayList and use current time to allow for the reminder
        // to be built.
        ZonedDateTime testTime = ZonedDateTime.now();

        Appointment app1 = new Appointment("Test Title", "Test Description",
                contact1, testTime, testTime);

        Appointment app2 = new Appointment("Test Title", "Test Description",
                contact2, testTime, testTime);

        Appointment app3= new Appointment("Test Title", "Test Description",
                contact3, testTime, testTime);

        Appointment app4 = new Appointment("Test Title", "Test Description",
                contact4, testTime, testTime);

        Appointment app5 = new Appointment("Test Title", "Test Description",
                contact5, testTime, testTime);

        appointmentApp.addAppointments(app1, app2, app3, app4, app5);


        // Trigger reminders
        appointmentApp.checkAndSendReminders();

//        // Display appointment information - Can be used if the list of the appointments still needs to be
          // printed out regardless if the reminder is triggered.
//        System.out.println("List of Appointments: ");
//        for (Appointment appointment : appointmentApp.appointmentsList) {
//            System.out.println("\tTitle: " + appointment.getTitle());
//            System.out.println("\tDescription: " + appointment.getDescription());
//            System.out.println("\tContact: " + appointment.getContact());
//            System.out.println("\tAppointment Time: " + appointment.getAppointmentTime());
//            System.out.println("\tReminder Time: " + appointment.getReminderTime() + "\n");
//        }

        System.out.println("\nStarting Reminder Stream: \n");
        // Alternate way of printing the reminders if no toString override in the Reminder class.
        //appointmentApp.stream.forEach(rem -> System.out.println(rem.getText());
        appointmentApp.stream.forEach(System.out::print);

    }
}
