package com.spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.spring.models.Bill;
import com.spring.models.Patient;
import com.spring.services.BillService;
import com.spring.services.PatientService;
import com.spring.structures.ResponseStructure;

@RestController
public class PostmanAPI {

    @Autowired
    private PatientService patientService;
    @Autowired
    private BillService billService;

//    @GetMapping("/patients")
//    public List<Patient> viewAllPatients() {
//    	return patientService.getAllPatients(); 
//    }
    
    @GetMapping("/patients")
    public ResponseStructure<List<Patient>> viewAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        if (patients != null && !patients.isEmpty()) {
            return new ResponseStructure<>("success", 200, patients);
        } else {
            return new ResponseStructure<>("failed", 404, patients);
        }
    }

    
    
    

    @GetMapping("/patients/{age}")
    public List<Patient> viewAllPatientsbyAge(@PathVariable int age) {
    	return patientService.getPatientByAge(age); 
    }
    
    @GetMapping("/bills")
    public List<Bill> viewAllBills() {
    	return billService.getAllBills(); 
    }
    
    
    
//
//    // Open Add Patient Form
//    @GetMapping("/add")
//    public String showAddForm(Model model) {
//        return "patient/patient-add"; // add-patient.html
//    }
//
//    // Handle Add Patient Form
//    @PostMapping("/add")
//    public String addPatient(@ModelAttribute Patient patient) {
//        patientService.addPatient(patient);
//        return "redirect:/patient";
//    }
//
//    // Open Edit Form
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        Optional<Patient> optionalPatient = patientService.getPatientById(id);
//        if (optionalPatient.isPresent()) {
//            model.addAttribute("patient", optionalPatient.get());
//            return "patient/patient-edit"; // edit-patient.html
//        } else {
//            return "redirect:/patient";
//        }
//    }
//
//    // Handle Edit Patient
//    @PostMapping("/edit/{id}")
//    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
//        patientService.updatePatient(id, patient);
//        return "redirect:/patient";
//    }
//
//    // Delete Patient
//    @GetMapping("/delete/{id}")
//    public String deletePatient(@PathVariable Long id) {
//        patientService.deletePatient(id);
//        return "redirect:/patient";
//    }
//
//    // Delete All Patients
//    @GetMapping("/deleteAll")
//    public String deleteAllPatients() {
//        patientService.deleteAllPatient();
//        return "redirect:/patient";
//    }
}
