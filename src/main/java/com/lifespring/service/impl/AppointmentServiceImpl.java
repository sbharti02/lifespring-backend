package com.lifespring.service.impl;

import com.lifespring.dto.AppointmentDto;
import com.lifespring.dto.DoctorDto;
import com.lifespring.dto.PatientDto;
import com.lifespring.enums.Status;
import com.lifespring.model.Appointment;
import com.lifespring.model.Doctor;
import com.lifespring.model.Patient;
import com.lifespring.repository.AppointmentRepository;
import com.lifespring.service.AppointmentService;
import com.lifespring.service.DoctorService;
import com.lifespring.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private LocalDateTime slot;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ModelMapper modelMapper;


    private Appointment appointment = new Appointment();


    @Override
    public AppointmentDto createAppointment(UUID doctorId, UUID patientId) {

        //setting the UUId for the appointment
        appointment.setAppointmentId(UUID.randomUUID());

           /*one doctor can see a patient for 30 minutes
          if another request come for appointment we will increase it for 30 min
          ex like today - 17-08-2025 10:00Am get the first appointment
          17-08-2025  10:30 Am
        */
        if(slot !=null){
            slot = LocalDateTime.now();
            appointment.setAppointmentDateTime(slot);
        }else{
            slot = LocalDateTime.now().plusMinutes(30);
            appointment.setAppointmentDateTime(slot);
        }



        //setting doctor
        DoctorDto doctorById = doctorService.getDoctorById(doctorId);
        Doctor doctor = modelMapper.map(doctorById, Doctor.class);
        appointment.setDoctor(doctor);

        //setting the patient
        PatientDto patientDetailsById = patientService.getPatientDetailsById(patientId);
        Patient mapped = modelMapper.map(patientDetailsById, Patient.class);
        appointment.setPatient(mapped);


        //setting the status
        appointment.setStatus(Status.SCHEDULED);


        // Saving the data in the database
        Appointment savedAppointment = appointmentRepository.save(appointment);
        //converting the appointment to appointmentDto
         return  modelMapper.map(savedAppointment, AppointmentDto.class);
    }

    @Override
    public String deleteAppointment(UUID uuid) {
        Appointment appointment1 = appointmentRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Nothing found with the given id !"));
        appointmentRepository.delete(appointment1);
        return "appointment deleted !!";
    }

    @Override
    public List<AppointmentDto> getAllAppointment() {
        return appointmentRepository.findAll().stream().map(e-> modelMapper.map(e , AppointmentDto.class)).toList();

    }

    @Override
    public AppointmentDto getById(UUID uuid) {
        return appointmentRepository.findById(uuid).map(e->modelMapper.map(e,AppointmentDto.class)).orElseThrow(()-> new RuntimeException("Nothing found with the given id !!"));
    }
}
