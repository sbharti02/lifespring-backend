package com.lifespring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lifespring.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctors")
public class Doctor {

    //doctor field
    @Id
    @JdbcTypeCode(SqlTypes.CHAR) // use of this annotation is to store the UUID in the database as a CHAR type
    @Column(length = 38) // length is set to 38 to accommodate the UUID format
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID doctorId;
    @NotBlank(message = "doctor name is not valid !!")
    private String doctorName;
    @Email(message = "Not a valid email !!")
    private String email;
    @Size(min = 10,message = "phone number cannot be less then the 10 digit !!")
    private String phoneNumber;
    @NotNull(message = "Specialization cannot be null !!")
    private String specialization;
    private String hospitalName;
    @NotNull(message = "Address cannot be null !!")
    private String address;
    private int fees;
    private String licenseNo;
    private String profilePictureUrl;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private Role role;

    //doctor can see the patients
    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    List<Appointment> appointments;




}

