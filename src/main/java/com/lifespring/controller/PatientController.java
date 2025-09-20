package com.lifespring.controller;

import com.lifespring.dto.PatientDto;
import com.lifespring.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

   //Create Patient
    @PostMapping("/create")
    public ResponseEntity<PatientDto> create(@RequestParam("username") String username,@RequestBody PatientDto patientDto){
        PatientDto registeredPatient = patientService.registerPatient(username,patientDto);
        return new ResponseEntity<>(registeredPatient, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/update")
    public ResponseEntity<PatientDto> update(@RequestParam(name = "pId") UUID id , @RequestBody PatientDto patientDto){
        PatientDto updatePatientDto = patientService.update(id, patientDto);
        return new ResponseEntity<>(updatePatientDto,HttpStatus.OK);
    }

    //get by Id
    @GetMapping("/byId/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable UUID id){
        PatientDto patientDetailsById = patientService.getPatientDetailsById(id);
       return new ResponseEntity<>(patientDetailsById , HttpStatus.OK);
    }

    //get All
    @GetMapping("/all")
    public ResponseEntity<List<PatientDto>> get(){
        List<PatientDto> all = patientService.getAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    //get By Email
//    @GetMapping("/email")
//    public ResponseEntity<PatientDto> getByEmail(@RequestParam("mail") String email){
//        PatientDto patientDetailsByEmail = patientService.getPatientDetailsByEmail(email);
//        return new ResponseEntity<>(patientDetailsByEmail,HttpStatus.OK);
//    }

    //delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam("id") UUID id){
       patientService.delete(id);
       return new ResponseEntity<>("Patient delete successfully",HttpStatus.OK);

    }
}
