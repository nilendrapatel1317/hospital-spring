package com.spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.models.Bill;

import jakarta.transaction.Transactional;

@Repository
public interface BillRepository extends JpaRepository<Bill, String>{
	// Custom query to delete bills by patient id
    @Modifying
    @Transactional
    @Query("DELETE FROM Bill b WHERE b.patient.id = :patientId")
    void deleteBillsByPatientId(@Param("patientId") Long patientId);
    
    @Query(value = "SELECT id FROM bill ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLastId();
}
