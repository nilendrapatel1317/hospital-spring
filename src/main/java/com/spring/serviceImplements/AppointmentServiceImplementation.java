package com.spring.serviceImplements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.DTO.AppointmentDTO;
import com.spring.DTO.DoctorDTO;
import com.spring.DTO.PatientDTO;
import com.spring.models.Appointment;
import com.spring.models.Bill;
import com.spring.models.Doctor;
import com.spring.models.Patient;
import com.spring.repositories.AppointmentRepository;
import com.spring.repositories.DoctorRepository;
import com.spring.repositories.PatientRepository;
import com.spring.services.AppointmentService;

@Service
public class AppointmentServiceImplementation implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public List<Appointment> getAllAppointment() {
		return appointmentRepository.findAll();
	}

	@Override
	public List<Appointment> getAllAppointmentsSortBy(String sortBy) {
		return appointmentRepository.findAll(Sort.by(sortBy));
	}
	
	

	@Override
	public List<Appointment> getAppById(Appointment appointment) {
		Appointment appointment2 = appointmentRepository.findById(appointment.getId()).orElse(null);
		List<Appointment> list = new ArrayList<>();
		if(appointment2 != null) {
			list.add(appointment2);
		}
		return list;
	}

	@Override
	public Optional<Appointment> getAppointmentById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Appointment addAppointment(Appointment appointment) {
		try {
			// set appointment ID
			appointment.setId(generateID());
			
			// fetch doctor
			Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).orElse(null);

			// fetch patient
			Patient patient = patientRepository.findById(appointment.getPatient().getId()).orElse(null);

			// Add doctor and patient into appointment
			appointment.setDoctor(doctor);
			appointment.setPatient(patient);
//			appointment.setStatus(false);
			
			// Save Appointment
			Appointment saveAppointment = appointmentRepository.save(appointment);
			
			// Add bill into patient
			List<Appointment> doctorList = doctor.getAppointments();
			doctorList.add(appointment);
			doctor.setAppointments(doctorList);

			// Add bill into patient
			List<Appointment> patientList = patient.getAppointments();
			patientList.add(appointment);
			patient.setAppointments(patientList);

			return saveAppointment;
		} catch (Exception e) {
			return null;
		}
	}
	
	private String generateID() {
		String lastIdStr = appointmentRepository.findLastId().orElse("APP-100");
		int lastNum = Integer.parseInt(lastIdStr.split("-")[1]);
		String newId = "APP-" + (lastNum + 1);
		return newId;
	}

	@Override
	public AppointmentDTO addAppointmentDTO(Appointment appointment) {
		// Step 1: Fetch full doctor and patient from DB using ID
		Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
				.orElseThrow(() -> new RuntimeException("Doctor not found"));

		Patient patient = patientRepository.findById(appointment.getPatient().getId())
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		// Step 2: Set doctor and patient to appointment object
		appointment.setDoctor(doctor);
		appointment.setPatient(patient);

		// ✅ Step 3: Save appointment to DB
		Appointment savedAppointment = appointmentRepository.save(appointment);

		// Step 4: Prepare DTOs
		DoctorDTO doctorDTO = new DoctorDTO();
		doctorDTO.setId(doctor.getId());
		doctorDTO.setName(doctor.getName());

		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setId(patient.getId());
		patientDTO.setName(patient.getName());
		patientDTO.setGender(patient.getGender());

		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setId(savedAppointment.getId());
		appointmentDTO.setAppointmentDate(savedAppointment.getAppointmentDate());
		appointmentDTO.setDoctor(doctorDTO);
		appointmentDTO.setPatient(patientDTO);

		return appointmentDTO;
	}

	@Override
	public Appointment updateAppointmentDate(String id, Appointment updateAppointment) {
		Appointment existAppointment = appointmentRepository.findById(id).orElse(null);

		// Update Appointment Date
		existAppointment.setAppointmentDate(updateAppointment.getAppointmentDate());
		
		return appointmentRepository.save(existAppointment);
	}

	@Override
	public Appointment updateAppointmentStatus(String id, Appointment updateAppointment) {
		Appointment existAppointment = appointmentRepository.findById(id).orElse(null);
		
		// Update Appointment Date
		existAppointment.setStatus(true);
		
		return appointmentRepository.save(existAppointment);
	}

	@Override
	public String deleteAppointment(String id) {
		if (appointmentRepository.existsById(id)) {
			Appointment appointment = appointmentRepository.findById(id).orElse(null);
			Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).orElse(null);
			Patient patient = patientRepository.findById(appointment.getPatient().getId()).orElse(null);

			// remove appointment from doctor and patient
			doctor.getAppointments().remove(appointment);
			patient.getAppointments().remove(appointment);

			// remove doctor and patient from appointment
			appointment.setDoctor(null);
			appointment.setPatient(null);

			appointmentRepository.deleteById(id);
			return "Appointment Deleted Successfully !!";
		} else
			return "Appointment Not Found !!";
	}

	@Override
	public String deleteAllAppointment() {
		appointmentRepository.deleteAll();
		return "All Appointment Deleted Successfully !!";
	}

}
