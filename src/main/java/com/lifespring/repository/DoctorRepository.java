package com.lifespring.repository;

import com.lifespring.dto.DoctorDto;
import com.lifespring.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

    @Override
    Optional<Doctor> findById(UUID uuid);

    Optional<List<Doctor>> findBySpecialization(String specialization);


}
