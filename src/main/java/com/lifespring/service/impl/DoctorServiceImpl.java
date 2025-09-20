package com.lifespring.service.impl;

import com.lifespring.dto.DoctorDto;
import com.lifespring.model.Doctor;
import com.lifespring.model.Patient;
import com.lifespring.repository.DoctorRepository;
import com.lifespring.enums.Role;
import com.lifespring.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    private ModelMapper mapper;

    public DoctorServiceImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public DoctorDto registerDoctor(DoctorDto doctorDto) {
        // Logic to register a doctor
        doctorDto.setDoctorId(UUID.randomUUID());
        doctorDto.setRole(Role.DOCTOR);
        Doctor doctor = mapper.map(doctorDto, Doctor.class);
        doctorRepository.save(doctor);

        return mapper.map(doctor,DoctorDto.class);

    }

    @Override
    public DoctorDto updateDoctor(UUID uuid,DoctorDto doctorDto) {

        return doctorRepository.findById(uuid)
                .map(e -> {
                    if (doctorDto.getDoctorName() != null) {
                        e.setDoctorName(doctorDto.getDoctorName());
                    }
                    if (doctorDto.getEmail() != null) {
                        e.setEmail(doctorDto.getEmail());
                    }
                    if (doctorDto.getFees() != doctorDto.getFees()) {
                        e.setFees(doctorDto.getFees());
                    }
                    if (doctorDto.getEmail() != null) {
                        e.setEmail(doctorDto.getEmail());
                    }
                    if (doctorDto.getAddress() != null) {
                        e.setAddress(doctorDto.getAddress());
                    }
                    if (doctorDto.getSpecialization() != null) {
                        e.setSpecialization(doctorDto.getSpecialization());
                    }
                    if (doctorDto.getLicenseNo() != null) {
                        e.setLicenseNo(doctorDto.getLicenseNo());
                    }
                    if (doctorDto.getHospitalName() != null) {
                        e.setHospitalName(doctorDto.getHospitalName());
                    }
                    Doctor saved = doctorRepository.save(e);
                    return mapper.map(saved, DoctorDto.class);
                })
                .orElseThrow(() -> new RuntimeException("Doctor not found with the given id"));


    }

    @Override
    public List<DoctorDto> getAllDoctor() {
      return doctorRepository.findAll().stream().map(p->{
           return mapper.map(p,DoctorDto.class);
       }).collect(Collectors.toList());
    }

    @Override
    public DoctorDto getDoctorById(UUID docId) {
       return doctorRepository.findById(docId)
               .map(e-> mapper.map(e,DoctorDto.class))
               .orElseThrow(()-> new RuntimeException("No doctor found with the given id"));

    }

    @Override
    public List<Patient> getDoctorPatient(UUID docId) {
        return List.of();
    }

    @Override
    public String delete(UUID docId) {
        Doctor doctor = doctorRepository.findById(docId).orElseThrow(() -> new RuntimeException("No doctor is found with the given id !!"));
        doctorRepository.delete(doctor);

        return "Doctor deleted success fully !!";
    }

    @Override
    public List<DoctorDto> getDoctorBySpecialization(String specialization) {

        List<Doctor> doctors = doctorRepository.findBySpecialization(specialization).orElseThrow(() -> new RuntimeException("No doctor found with the given specialization"));

        return doctors.stream().map(e-> {
            return mapper.map(e, DoctorDto.class);
        }).collect(Collectors.toList());


    }
}
