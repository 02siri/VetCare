package au.edu.rmit.sept.webapp.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @PostConstruct
    public void initialize(){
        try{
            appointmentRepository.setup();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating appt table");
        }
    }
    @Override
//    public void insertAppointment(Appointment appointment){
//        appointmentRepository.insert(appointment);
//    }
    public void insertAppointment(Appointment appointment) {
        System.out.println("Generated Appointment Number: " + appointment.getApptNum());
        appointmentRepository.insert(appointment);
    }


    @Override
    public Appointment findApptNo(String apptNo) {
       return appointmentRepository.findApptNo(apptNo);
    }

    @Override
    public void updateAppt(Appointment appt) {
        appointmentRepository.update(appt);
    }

    @Override
    public void cancelAppt(Appointment appt) {
        appointmentRepository.cancel(appt);
    }

    @Override
    public boolean isTimeSlotBooked(String clinic, String doctor, String date, String time){
        return appointmentRepository.isTimeSlotBooked(clinic, doctor, date, time);
    }

    @Override
    public boolean isCancellationLate(Appointment appt){
        return appointmentRepository.isCancellationLate(appt);
    }



}
