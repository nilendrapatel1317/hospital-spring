package com.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.DTO.AppointmentDTO;
import com.spring.models.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, String> {

	AppointmentDTO save(AppointmentDTO appointmentDTO);
	
	@Query(value = "SELECT id FROM appointment ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLastId();
}
