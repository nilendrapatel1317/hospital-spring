package com.spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.models.Appointment;
import com.spring.models.Doctor;
import com.spring.services.AppointmentService;
import com.spring.services.DoctorService;
import com.spring.services.PatientService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;

	// Open All Appointment
	@GetMapping
    public String getAllAppointments(@RequestParam(name = "sortBy", required = false) String sortBy, Model model) {
        List<Appointment> appointments;

        if (sortBy == null || sortBy.isEmpty()) {
            appointments = appointmentService.getAllAppointment(); // Default by ID
            model.addAttribute("sortField", "ID");
        } else {
            appointments = appointmentService.getAllAppointmentsSortBy(sortBy);
            model.addAttribute("sortField", sortBy);
        }

        model.addAttribute("appointments", appointments);
        return "appointment/appointments-list";
    }
	@PostMapping
	public String viewAppointmentById(@ModelAttribute Appointment appointment, Model model) {
		List<Appointment> appointments;
		
		if (appointment.getId().trim() != "") {
			appointments = appointmentService.getAppById(appointment); // Default by ID
			model.addAttribute("appointments", appointments);
			model.addAttribute("sortField", "ID");
			return "appointment/appointments-list";
		} else {
			return "redirect:/appointment";
		}
		
	}

	// Open Add Appointment Form
	@GetMapping("/add")
	public String showAddAppointmentForm(Model model) {
		model.addAttribute("appointment", new Appointment());
		model.addAttribute("doctors", doctorService.getAllDoctors());
		model.addAttribute("patients", patientService.getAllPatients());
		return "appointment/appointment-add";
	}

	// Submit Add Appointment Form
	@PostMapping("/add")
	public String addAppointment(@ModelAttribute Appointment appointment) {
		appointmentService.addAppointment(appointment);
		return "redirect:/appointment";
	}
	
	// Open Edit Appointment Form
	@GetMapping("/edit-date/{id}")
	public String showEditFormForDate(@PathVariable String id, Model model) {

		Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
		if (appointment.isPresent()) {
			model.addAttribute("appointment", appointment.get()); // not Optional
    		model.addAttribute("doctors", doctorService.getAllDoctors());
    		model.addAttribute("patients", patientService.getAllPatients());
            return "appointment/appointment-date-edit"; 
        } else {
            return "redirect:/appointment";
        }
	}
	// Open Edit Appointment Form
	@GetMapping("/edit-status/{id}")
	public String showEditFormForStatus(@PathVariable String id, Model model) {
		
		Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
		if (appointment.isPresent()) {
			model.addAttribute("appointment", appointment.get()); // not Optional
			model.addAttribute("doctors", doctorService.getAllDoctors());
			model.addAttribute("patients", patientService.getAllPatients());
			return "appointment/appointment-status-edit"; 
		} else {
			return "redirect:/appointment";
		}
	}

	// Submit Edit Appointment
	@PostMapping("/edit-date/{id}")
	public String updateAppointmentDate(@PathVariable String id, @ModelAttribute Appointment appointment) {
		appointmentService.updateAppointmentDate(id, appointment);
		return "redirect:/appointment";
	}

	// Submit Edit Appointment
	@PostMapping("/edit-status/{id}")
	public String updateAppointmentStatus(@PathVariable String id, @ModelAttribute Appointment appointment) {
		appointmentService.updateAppointmentStatus(id, appointment);
		return "redirect:/appointment";
	}

	// Delete Appointment
	@GetMapping("/delete/{id}")
	public String deleteAppointment(@PathVariable String id) {
		appointmentService.deleteAppointment(id);
		return "redirect:/appointment";
	}
	
	@GetMapping("/find/{id}")
	public Optional<Appointment> getAppointmentsById(@PathVariable String id) {
		return null;
	}


	@DeleteMapping("/deleteAll")
	public String deleteAllAppointment() {
		return appointmentService.deleteAllAppointment();
	}

}
