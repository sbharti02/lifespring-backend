package com.lifespring.controller;

import com.lifespring.dto.AppointmentDto;
import com.lifespring.exception.InvalidCredentialsException;
import com.lifespring.service.AppointmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    //create
    @PostMapping("/create/{docId}/{patId}")
    public ResponseEntity<AppointmentDto> createAppointment(@PathVariable("docId") UUID doctorId ,@PathVariable("patId") UUID patientId){
        try{
        AppointmentDto appointment = appointmentService.createAppointment(doctorId, patientId);
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        }catch (Exception e){
            log.info("Exception occurred while creating appointment: " + e.getMessage());
            throw new InvalidCredentialsException("You entered invalid doctorId or patientId");
        }
    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAppointment(@RequestParam("id") UUID uuid){
        String deleted = appointmentService.deleteAppointment(uuid);
        return new ResponseEntity<>(deleted,HttpStatus.OK);
    }

    //get all appointment
    @GetMapping("/getAll")
    public ResponseEntity<List<AppointmentDto>> getAllAppointment(){
        List<AppointmentDto> allAppointment = appointmentService.getAllAppointment();
        return new ResponseEntity<>(allAppointment, HttpStatus.OK);
    }
    //get by id
    @GetMapping("/getById/{id}")
    public ResponseEntity<AppointmentDto> getById(@PathVariable("id") UUID uuid) {
        AppointmentDto appointmentDto = appointmentService.getById(uuid);
        return new ResponseEntity<>(appointmentDto, HttpStatus.OK);
    }
}
