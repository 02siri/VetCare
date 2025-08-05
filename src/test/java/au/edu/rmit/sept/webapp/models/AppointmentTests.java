package au.edu.rmit.sept.webapp.models;

import au.edu.rmit.sept.webapp.controllers.AppointmentController;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentTests {

    @InjectMocks
    private AppointmentController appointmentController;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookAppointment_TimeSlotBooked() {
        String type = "Checkup";
        String date = LocalDate.now().plusDays(1).toString(); // Future date
        String time = "10:00 AM";
        String clinic = "Happy Paws Clinic";
        String doctor = "Dr. Smith";

        when(appointmentService.isTimeSlotBooked(clinic, doctor, date, time)).thenReturn(true);

        String result = appointmentController.bookAppointment(type, date, time, clinic, doctor, redirectAttributes);

        assertEquals("redirect:/appointment", result);
        verify(redirectAttributes).addFlashAttribute("errorMsg_Book", "The selected time slot is already booked. Please choose another time");
    }

    @Test
    void testRescheduleAppointment_TimeSlotBooked() {
        String apptNo = "APPT-1234";
        String newDate = LocalDate.now().plusDays(2).toString(); // Future date
        String newTime = "11:00 AM";
        String clinic = "Happy Paws Clinic";
        String doctor = "Dr. Smith";

        Appointment appointment = new Appointment();
        appointment.setApptNum(apptNo);
        when(appointmentService.findApptNo(apptNo)).thenReturn(appointment);
        when(appointmentService.isTimeSlotBooked(clinic, doctor, newDate, newTime)).thenReturn(true);

        String result = appointmentController.rescheduleAppointment(apptNo, newDate, newTime, clinic, doctor, redirectAttributes);

        assertEquals("redirect:/appointment", result);
        verify(redirectAttributes).addFlashAttribute("errorMsg_Reschedule", "The selected time slot is already booked. Please choose another time");
    }

    @Test
    void testCancelAppointment_ApptNoNotFound() {
        String apptNo = "APPT-1234";

        when(appointmentService.findApptNo(apptNo)).thenReturn(null);

        String result = appointmentController.cancelAppointment(apptNo, redirectAttributes);

        assertEquals("redirect:/appointment", result);
        verify(redirectAttributes).addFlashAttribute("errorMsg_Cancel", "Appointment not found with number: " + apptNo);
    }

}
