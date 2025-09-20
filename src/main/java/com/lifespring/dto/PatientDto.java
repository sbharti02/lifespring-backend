package com.lifespring.dto;

import com.lifespring.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private UUID patientId;
    private String patientName;
    private String email;
    private String phoneNumber;
    private String address;
    private LocalDate  dateOfBirth;
    private String bloodGroup;
    private String medicalHistory;
    private boolean allergic;
    private String allergyDetails;
    private String emergencyContactNumber;
    private String imageUrl;
    private String role;

}
