package com.lifespring.service;

import com.lifespring.dto.DoctorDto;
import com.lifespring.model.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorService {

    // create
    public DoctorDto registerDoctor(DoctorDto doctorDto);

    // update
    public DoctorDto updateDoctor(UUID uuid, DoctorDto doctorDto);

    //get all
    public List<DoctorDto> getAllDoctor();

    //get by id
    public DoctorDto getDoctorById(UUID docId);

    // get patients of the doctor
    public List<Patient> getDoctorPatient(UUID docId);

    //delete
    public String delete(UUID docId);

    //find doctor by specialization
    public List<DoctorDto>  getDoctorBySpecialization(String specialization);

}
