package com.lifespring.service.impl;

import com.lifespring.config.AppConstant;
import com.lifespring.dto.PatientDto;
import com.lifespring.dto.UserDto;
import com.lifespring.exception.ResourceNotFoundException;
import com.lifespring.exception.UserAlreadyExistsException;
import com.lifespring.model.Patient;
import com.lifespring.model.User;
import com.lifespring.repository.PatientRepository;
import com.lifespring.enums.Role;
import com.lifespring.repository.UserRepository;
import com.lifespring.service.PatientService;
import com.lifespring.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;


    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public PatientDto registerPatient(String username , PatientDto patientDto) {

        // Suppose user logged in and I don't want that a particular user which has a role -> patient
        // can create multiple patient

        // get the user object
            User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("No user found "));
            boolean isPresent = patientRepository.existsByUser_UserId(user.getUserId());
            if(isPresent){
                throw new UserAlreadyExistsException("The given patient is already created !!");
            }

        //map the user


        patientDto.setPatientId(UUID.randomUUID());
       patientDto.setRole(AppConstant.PATIENT);

       // converting dto to patient

        Patient mappedPatient = modelMapper.map(patientDto, Patient.class);

        mappedPatient.setUser(user);

        // Saved the patient
        Patient savedPatient = patientRepository.save(mappedPatient);

        // converting the savedPatient to dto

 return modelMapper.map(savedPatient, PatientDto.class);


    }

    @Override
    public PatientDto getPatientDetailsById(UUID patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("No patient found with the given id !!"));
        //converting patient into the dto

        return modelMapper.map(patient, PatientDto.class);

    }

//    @Override
//    public PatientDto getPatientDetailsByEmail(String email) {
//        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No patient found with the given email !!"));
//
//        return  modelMapper.map(patient, PatientDto.class);
//
//
//    }

    @Override
    public PatientDto  update(UUID id ,PatientDto patientDto) {
      return patientRepository.findById(id)
              .map(p -> {
                  if(patientDto.getPatientName()!=null){
                      p.setPatientName(patientDto.getPatientName());
                  }
                  if (patientDto.getAddress()!=null){
                      p.setAddress(patientDto.getAddress());
                  }

                  if(patientDto.getBloodGroup()!=null){
                      p.setBloodGroup(patientDto.getBloodGroup());
                  }
                  if(patientDto.getAllergyDetails()!=null){
                      p.setAllergyDetails(patientDto.getAllergyDetails());
                  }
                  if(patientDto.getDateOfBirth()!=null){
                      p.setDateOfBirth(patientDto.getDateOfBirth());
                  }
                  if(patientDto.getEmergencyContactNumber()!=null){
                      p.setEmergencyContactNumber(patientDto.getEmergencyContactNumber());
                  }
                  if(patientDto.getMedicalHistory()!=null){
                      p.setMedicalHistory(patientDto.getMedicalHistory());
                  }
                  if(patientDto.getPhoneNumber()!=null){
                      p.setPhoneNumber(patientDto.getPhoneNumber());
                  }

                  patientRepository.save(p);
                  return modelMapper.map(p, PatientDto.class);
              }).orElseThrow(()-> new RuntimeException("No patient found with the given id "));


    }

    @Override
    public String delete(UUID patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(()-> new ResourceNotFoundException("Not Patient found with the given id !!"));
        patientRepository.delete(patient);
        return "Patient deleted successfully !!";
    }

    @Override
    public List<PatientDto> getAll() {

        List<Patient> patientList = patientRepository.findAll();
        return patientList.stream().map(e->{
            return modelMapper.map(e, PatientDto.class);
        }).collect(Collectors.toList());

    }
}
