package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.AppointmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.Authenticator;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class AppointmentController {

    private final AppointmentService apptService;

    @Autowired
    public AppointmentController(AppointmentService apptService){
        this.apptService = apptService;
    }

    @GetMapping("/appointment")
    public String appointmentForm(Model model) {

        List<String>availableDoctors = Arrays.asList("Dr. Smith", "Dr. Brown", "Dr. Neva");
        List<String>availableClinics = Arrays.asList("Happy Paws Clinic", "The Great Animal Clinic", "Floof Clinic");
        model.addAttribute("availableDoctors",availableDoctors);
        model.addAttribute("availableClinics",availableClinics);
        return "appointment"; // Returns the appointment.html view
    }

    @PostMapping("/appointment/book")
    public String bookAppointment(
            @RequestParam("type") String type,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("clinic") String clinic,
            @RequestParam("doctor") String doctor,
            RedirectAttributes redirectAttributes) {

        LocalDate apptDate = LocalDate.parse(date);

        if(apptDate.isBefore(LocalDate.now())){
            redirectAttributes.addFlashAttribute("errorMsg_Book","You cannot book an appointment for past dates");
            return "redirect:/appointment";
        }

        if(apptService.isTimeSlotBooked(clinic, doctor, date, time)){
            System.out.println("Slot already booked!");
            redirectAttributes.addFlashAttribute("errorMsg_Book",
                    "The selected time slot is already booked. Please choose another time");
            return "redirect:/appointment";
        }

        Appointment appointment = new Appointment();
        appointment.setType(type);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setClinic(clinic);
        appointment.setDoctor(doctor);

        apptService.insertAppointment(appointment);

        redirectAttributes.addFlashAttribute("successMsg_Book",
                "Your appointment has been booked successfully with " + doctor + "! Thank You ! Appointment number: " +appointment.getApptNum());
        return "redirect:/appointment";
    }

    @PostMapping("/appointment/reschedule")
    public String rescheduleAppointment(
            @RequestParam("apptNo") String apptNo,
            @RequestParam("newDate") String newDate,
            @RequestParam("newTime") String newTime,
            @RequestParam("clinic") String clinic,
            @RequestParam("doctor") String doctor,
            RedirectAttributes redirectAttributes) {

        Appointment appt = apptService.findApptNo(apptNo);
        if(appt == null){
            redirectAttributes.addFlashAttribute("errorMsg_Reschedule","Appointment not found with number: " + apptNo);
            return "redirect:/appointment";
        }

        if(apptService.isTimeSlotBooked(clinic, doctor, newDate, newTime)){
            redirectAttributes.addFlashAttribute("errorMsg_Reschedule",
                    "The selected time slot is already booked. Please choose another time");
            return "redirect:/appointment";
        }

        appt.setDate(newDate);
        appt.setTime(newTime);
        appt.setClinic(clinic);
        appt.setDoctor(doctor);

        apptService.updateAppt(appt);

        redirectAttributes.addFlashAttribute("successMsg_Reschedule",
                "Appointment has been Rescheduled with " + doctor + "!");
        return "redirect:/appointment";
    }

    @PostMapping("/appointment/cancel")
    public String cancelAppointment(
            @RequestParam("apptNo") String apptNo,
            RedirectAttributes redirectAttributes){

        Appointment appt = apptService.findApptNo(apptNo);

        if(appt == null){
            redirectAttributes.addFlashAttribute("errorMsg_Cancel","Appointment not found with number: " + apptNo);
            return "redirect:/appointment";
        }

        if(apptService.isCancellationLate(appt)){
            redirectAttributes.addFlashAttribute("errorMsg_Cancel",
                    "Cancellation too late. A cancellation fee of $50 may apply");
        }
        apptService.cancelAppt(appt);

        redirectAttributes.addFlashAttribute("successMsg_Cancel","Appointment has been Cancelled");
        return "redirect:/appointment";

    }
}