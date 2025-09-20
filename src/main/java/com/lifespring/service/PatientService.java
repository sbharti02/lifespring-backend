package com.lifespring.service;

import com.lifespring.dto.PatientDto;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    // Method to register a new patient
    public PatientDto registerPatient(String username , PatientDto patientDto);

    //Method to get patient details by IDS
    public PatientDto getPatientDetailsById(UUID patientId);

    //Method to get paitent details by email
//    public PatientDto getPatientDetailsByEmail(String email);

    // Method to update patient details
    public PatientDto  update(UUID id , PatientDto patientDto);

            // Method to delete a patient

    public String  delete(UUID patientId);

    // Method to get All the patient
    public List<PatientDto> getAll();


}
