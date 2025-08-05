package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AppointmentControllerTests {

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
    void testAppointmentForm() {
        appointmentController.appointmentForm(model);
        verify(model, times(1)).addAttribute(eq("availableDoctors"), any());
        verify(model, times(1)).addAttribute(eq("availableClinics"), any());
        assertEquals("appointment", appointmentController.appointmentForm(model));
    }

    @Test
    void testBookAppointmentWithPastDate() {
        String date = LocalDate.now().minusDays(1).toString();
        String time = "10:00 AM";
        String clinic = "Happy Paws Clinic";
        String doctor = "Dr. Smith";
        appointmentController.bookAppointment("checkup", date, time, clinic, doctor, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("errorMsg_Book", "You cannot book an appointment for past dates");
    }

    @Test
    void testBookAppointmentWhenSlotIsBooked() {
        String date = LocalDate.now().plusDays(1).toString();
        String time = "10:00 AM";
        String clinic = "Happy Paws Clinic";
        String doctor = "Dr. Smith";

        when(appointmentService.isTimeSlotBooked(clinic, doctor, date, time)).thenReturn(true);

        appointmentController.bookAppointment("checkup", date, time, clinic, doctor, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("errorMsg_Book", "The selected time slot is already booked. Please choose another time");
    }
}
