package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Appointment;

import java.util.List;

public interface AppointmentService {
    void insertAppointment(Appointment appointment);
    Appointment findApptNo(String apptNo);
    void updateAppt(Appointment appt);
    void cancelAppt(Appointment appt);
    boolean isTimeSlotBooked(String clinic, String doctor, String date, String time);
    boolean isCancellationLate(Appointment appt);
}
