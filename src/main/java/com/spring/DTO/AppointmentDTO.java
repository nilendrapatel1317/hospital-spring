package com.spring.DTO;

import java.time.LocalDate;

//AppointmentDTO.java
public class AppointmentDTO {
	private String id;
	private LocalDate appointmentDate;
	private DoctorDTO doctor;
	private PatientDTO patient;
	
	

	public AppointmentDTO() {}

	public AppointmentDTO(String id, LocalDate appointmentDate, DoctorDTO doctor, PatientDTO patient) {
		this.id = id;
		this.appointmentDate = appointmentDate;
		this.doctor = doctor;
		this.patient = patient;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public DoctorDTO getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorDTO doctor) {
		this.doctor = doctor;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

}
