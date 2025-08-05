package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Appointment;

import java.sql.SQLException;
import java.util.List;

public interface AppointmentRepository {
    void setup() throws SQLException;
    void insert(Appointment appt);
    Appointment findApptNo(String apptNo);
    void update(Appointment appt);
    void cancel(Appointment appt);
    boolean isTimeSlotBooked(String clinic, String doctor, String date, String time);
    boolean isCancellationLate(Appointment appt);
}
