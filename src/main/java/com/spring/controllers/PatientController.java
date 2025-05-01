package com.spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.spring.models.Patient;
import com.spring.services.PatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Open Patient Page (List View)
    @GetMapping
    public String viewAllPatients(@RequestParam(name = "sortBy", required = false) String sortBy, Model model) {
        List<Patient> patients;

        if (sortBy == null || sortBy.isEmpty()) {
            patients = patientService.getAllPatients(); // default sorting
            model.addAttribute("sortField", "ID");
        } else {
            patients = patientService.getAllPatientsBySort(sortBy);
            model.addAttribute("sortField", sortBy);
        }

        model.addAttribute("patients", patients);
        return "patient/patients-list";
    }

    @PostMapping
    public String findById(@ModelAttribute Patient patient, Model model) {
    	
    	List<Patient> patients;
    	
    	if(patient.getId().trim() != "") {
    		patients = patientService.getPatientById(patient);    	
    		model.addAttribute("patients", patients);
    		model.addAttribute("sortField", "ID");
    		return "patient/patients-list";
    	}
    	else {
    		return "redirect:/patient";
		}
    }
    

    // Open Add Patient Form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        return "patient/patient-add"; // add-patient.html
    }

    // Handle Add Patient Form
    @PostMapping("/add")
    public String addPatient(@ModelAttribute Patient patient) {
        patientService.addPatient(patient);
        return "redirect:/patient";
    }

    // Open Edit Form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<Patient> optionalPatient = patientService.getPatientById(id);
        if (optionalPatient.isPresent()) {
            model.addAttribute("patient", optionalPatient.get());
            return "patient/patient-edit"; // edit-patient.html
        } else {
            return "redirect:/patient";
        }
    }

    // Handle Edit Patient
    @PostMapping("/edit/{id}")
    public String updatePatient(@PathVariable String id, @ModelAttribute Patient patient) {
        patientService.updatePatient(id, patient);
        return "redirect:/patient";
    }

    // Delete Patient
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return "redirect:/patient";
    }

    // Delete All Patients
    @GetMapping("/deleteAll")
    public String deleteAllPatients() {
        patientService.deleteAllPatient();
        return "redirect:/patient";
    }
}
