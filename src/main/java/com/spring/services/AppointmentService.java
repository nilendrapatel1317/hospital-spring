package com.spring.services;

import java.util.List;
import java.util.Optional;

import com.spring.DTO.AppointmentDTO;
import com.spring.models.Appointment;

public interface AppointmentService {

	//For Fetch
	public List<Appointment> getAllAppointment();

	public List<Appointment> getAllAppointmentsSortBy(String sortBy);
	
	public List<Appointment> getAppById(Appointment appointment);
	
	public Optional<Appointment> getAppointmentById(String id);

	
	// For Add
	public Appointment addAppointment(Appointment appointment);
	
	//For Security, structure, maintainability
	public AppointmentDTO addAppointmentDTO(Appointment appointment);

	
	// For Update
	public Appointment updateAppointmentDate(String id, Appointment appointment);

	public Appointment updateAppointmentStatus(String id, Appointment appointment);

	
	// For Delete
	public String deleteAppointment(String id);
	
	public String deleteAllAppointment();


}
