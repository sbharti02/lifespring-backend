package com.lifespring.service;

import com.lifespring.dto.AppointmentDto;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    // create appointment
    public AppointmentDto createAppointment( UUID doctorId , UUID patientId);

    //delete appointment
    public String deleteAppointment(UUID uuid);

    //get Appointment
    public List<AppointmentDto> getAllAppointment();

    //get by id
    public AppointmentDto getById(UUID uuid);
}
