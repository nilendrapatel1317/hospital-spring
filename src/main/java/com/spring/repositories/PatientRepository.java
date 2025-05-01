package com.spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.models.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
	
//	// Custom Query
	List<Patient> findByAge(int age);

	List<Patient> findByAgeAndName(int age, String name);
	
	@Query(value = "SELECT id FROM patient ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLastId();



}
