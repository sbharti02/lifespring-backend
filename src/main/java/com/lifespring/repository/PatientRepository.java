package com.lifespring.repository;

import com.lifespring.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID>{

    // get patient by id
    Optional<Patient> findById(UUID patientId);

    boolean existsByUser_UserId(UUID userId);

}
