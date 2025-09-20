package com.lifespring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {
    private UUID appointmentId;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    private LocalDateTime appointmentDateTime;
    private UUID doctorId;
    private String doctorName;
    private String doctorEmail;
    private String doctorPhone;
    private String doctorSpecialization;

    // patient details
    private UUID patientId;
    private String patientName;
    private String patientEmail;
    private String patientPhone;
    private String status;


}
