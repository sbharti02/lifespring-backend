package com.lifespring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lifespring.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@Table(name = "patients")
public class Patient {
@Id
@JdbcTypeCode(SqlTypes.CHAR)
@Column(length = 36)
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
private UUID  patientId;
@Column(name = "p_name")
private String patientName;
@Email
private String email;
@Column(name = "phone")
@Size(min=10, message = "Phone number must be at least 10 digits")
private String phoneNumber;
@NotBlank
private String address;
@Column(name="dob")
@DateTimeFormat(pattern = "yyyy-MM-dd")
private LocalDate dateOfBirth;
@NotBlank(message = "Blood Group cannot be blank")
private String bloodGroup;
private String  medicalHistory;
private boolean allergic;
private String allergyDetails;
@Column(name = "emergency_contact")
@NotBlank(message = "Emergency contact number cannot be blank")
@Size(min = 10 ,message = "Emergency contact number must be at least 10 digits")
private String emergencyContactNumber;
private String imageUrl;

private String role;

// patient is also have a Doctor
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Appointment> appointments;

    // Patient is associated with the user

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

}
