package com.spring.serviceImplements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.models.Appointment;
import com.spring.models.Bill;
import com.spring.models.Patient;
import com.spring.repositories.AppointmentRepository;
import com.spring.repositories.BillRepository;
import com.spring.repositories.PatientRepository;
import com.spring.services.BillService;
import com.spring.services.PatientService;

@Service
public class PatientServiceImplementation implements PatientService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private BillService billService;
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@Override
	public List<Patient> getAllPatientsBySort(String sortBy) {
		return patientRepository.findAll(Sort.by(sortBy));
	}

	@Override
	public Optional<Patient> getPatientById(String id) {
		return patientRepository.findById(id);
	}

	@Override
	public List<Patient> getPatientByAge(int age) {
		return patientRepository.findByAge(age);
	}

	@Override
	public List<Patient> getPatientByAge(int age, String name) {
		return patientRepository.findByAgeAndName(age, name);
	}

	@Override
	public Patient addPatient(Patient patient) {
		patient.setId(generateID());
		return patientRepository.save(patient);
	}

	private String generateID() {
		String lastIdStr = patientRepository.findLastId().orElse("PAT-100");
		int lastNum = Integer.parseInt(lastIdStr.split("-")[1]);
		String newId = "PAT-" + (lastNum + 1);
		return newId;
	}

	@Override
	public Patient updatePatient(String id, Patient updatedPatient) {
		Optional<Patient> optionalPatient = patientRepository.findById(id);
		if (optionalPatient.isPresent()) {
			// fetch old data
			Patient existingPatient = optionalPatient.get();

			// Update Whatever you want
			existingPatient.setName(updatedPatient.getName());
			existingPatient.setAge(updatedPatient.getAge());
			existingPatient.setGender(updatedPatient.getGender());
			existingPatient.setAddress(updatedPatient.getAddress());
			existingPatient.setPhone(updatedPatient.getPhone());

			return patientRepository.save(existingPatient);
		} else {
			throw new RuntimeException("Patient not found with id: " + id);
		}
	}

	@Override
	public String deletePatient(String id) {
		try {
			Patient patient = patientRepository.findById(id).orElse(null);

			// check if there any bill is unpaid or not
			if (patient.getBill() != null) {
				Bill bill = patient.getBill();

				if (bill.getStatus()) {
					return "Cannot delete patient. There are unpaid bills.";
				}
			}

			// Check if there any appointment is pending or not
			List<Appointment> appointments = patient.getAppointments();
			if (appointments.size() > 0) {
				for (Appointment appointment : appointments) {
					if (!appointment.getStatus()) {
						return "Cannot delete patient. There are pending Appointment.";
					}
				}
			}

			// Delete patient
			patientRepository.deleteById(id);

			return "Patient with ID " + id + " deleted successfully.";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	@Override
	public String deleteAllPatient() {
		patientRepository.deleteAll();
		return "All patients deleted successfully.";
	}

	@Override
	public List<Patient> getPatientById(Patient patient) {
		
		Patient patient1 = patientRepository.findById(patient.getId()).orElse(null);
		List<Patient> list = new ArrayList<>();
		if(patient1 != null) {
			list.add(patient1);
		}
		return list;
	}

}
