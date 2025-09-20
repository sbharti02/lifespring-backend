package com.lifespring.controller;

import com.lifespring.dto.DoctorDto;
import com.lifespring.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    //create
    @PostMapping("/create")
    public ResponseEntity<DoctorDto> create(@RequestBody DoctorDto doctorDto){
        try {
            DoctorDto doctorDtoRegistered = doctorService.registerDoctor(doctorDto);
            return new ResponseEntity<>(doctorDtoRegistered, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println("error while creating doctor account {}:"+e.getMessage());
        }

        return null;
    }

    //update
    @PutMapping("/update")
    public ResponseEntity<DoctorDto> update(@RequestParam("id") UUID uuid, @RequestBody DoctorDto doctorDto){
        DoctorDto updateDoctor = doctorService.updateDoctor(uuid, doctorDto);
        return new ResponseEntity<>(updateDoctor, HttpStatus.OK);
    }

    // get by specialization
    @GetMapping("/special/{specialization}")
    public ResponseEntity<List<DoctorDto>> getDoctorBySpecialization(@PathVariable String specialization){
        List<DoctorDto> doctorBySpecialization = doctorService.getDoctorBySpecialization(specialization);
        return new ResponseEntity<>(doctorBySpecialization, HttpStatus.OK);
    }

    //get All doctors
    @GetMapping("/all")
    public ResponseEntity<List<DoctorDto>> getAll(){
        List<DoctorDto> allDoctor = doctorService.getAllDoctor();
      return  new ResponseEntity<>(allDoctor,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") UUID uuid){
        String deleted = doctorService.delete(uuid);
        return new ResponseEntity<>(deleted,HttpStatus.OK);

    }
}
