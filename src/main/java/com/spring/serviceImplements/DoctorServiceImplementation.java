package com.spring.serviceImplements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.models.Appointment;
import com.spring.models.Doctor;
import com.spring.repositories.DoctorRepository;
import com.spring.services.DoctorService;

@Service
public class DoctorServiceImplementation implements DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

	@Override
	public List<Doctor> getAllDoctorsSortBy(String sortBy) {
		return doctorRepository.findAll(Sort.by(sortBy));
	}

	@Override
	public Optional<Doctor> getDoctorById(String id) {

		return doctorRepository.findById(id);
	}

	@Override
	public Doctor addDoctor(Doctor doctor) {
		doctor.setId(generateID());
		return doctorRepository.save(doctor);
	}

	private String generateID() {
		String lastIdStr = doctorRepository.findLastId().orElse("DOC-100");
		int lastNum = Integer.parseInt(lastIdStr.split("-")[1]);
		String newId = "DOC-" + (lastNum + 1);
		return newId;
	}

	@Override
	public Doctor updateDoctor(String id, Doctor updateDoctor) {
		// fetch old data
		Doctor exitsDoctor = doctorRepository.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));

		// Update required data
		exitsDoctor.setName(updateDoctor.getName());
		exitsDoctor.setSpecialization(updateDoctor.getSpecialization());
		exitsDoctor.setExperience(updateDoctor.getExperience());
		exitsDoctor.setPhone(updateDoctor.getPhone());

		return doctorRepository.save(exitsDoctor);
	}

	@Override
	public String deleteDoctor(String id) {
		if (doctorRepository.existsById(id)) {
			Doctor doctor = doctorRepository.findById(id).orElse(null);
			
			// Check if there any appointment is pending or not
			List<Appointment> appointments = doctor.getAppointments();
			for (Appointment appointment : appointments) {
				if (!appointment.getStatus()) {
					return "Cannot delete doctor. There are pending Appointment.";
				}
			}
			doctorRepository.deleteById(id);
			return "Doctor Delete SUccessfully !!";
		} else {
			return "Doctor Not Found";
		}
	}

	@Override
	public String deleteAllDoctor() {
		doctorRepository.deleteAll();
		return "All Doctors Deleted SUccessfully !!";
	}

	@Override
	public List<Doctor> getDoctorById(Doctor doctor) {
		Doctor doctor2 = doctorRepository.findById(doctor.getId()).orElse(null);
		List<Doctor> listOfDoctors = new ArrayList<>();
		if(doctor2 != null) {
			listOfDoctors.add(doctor2);
		}
		return listOfDoctors;
	}

}
