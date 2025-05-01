package com.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.spring.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {
	@Query(value = "SELECT id FROM doctor ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLastId();
}
