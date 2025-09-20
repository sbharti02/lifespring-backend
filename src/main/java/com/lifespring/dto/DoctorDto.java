package com.lifespring.dto;

import com.lifespring.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private UUID doctorId;
    private String doctorName;
    private String email;
    private String phoneNumber;
    private String specialization;
    private String hospitalName;
    private String address;
    private int fees;
    private String licenseNo;
    private String profilePictureUrl;
    private Role role;
    //doctor can see the patients
    private List<PatientDto> patient;



}
