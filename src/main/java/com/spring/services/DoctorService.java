package com.spring.services;

import java.util.List;
import java.util.Optional;

import com.spring.models.Doctor;

public interface DoctorService {

	public List<Doctor> getAllDoctors();

	public List<Doctor> getAllDoctorsSortBy(String sortBy);

	public List<Doctor> getDoctorById(Doctor doctor);

	public Optional<Doctor> getDoctorById(String id);

	public Doctor addDoctor(Doctor doctor);

	public Doctor updateDoctor(String id, Doctor doctor);

	public String deleteDoctor(String id);

	public String deleteAllDoctor();

}
