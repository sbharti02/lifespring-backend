package com.lifespring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lifespring.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 40)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID appointmentId;

    @JsonProperty(value = "scheduledAt", access = JsonProperty.Access.READ_ONLY)
    @Column(name = "scheduled_at")
    private LocalDateTime appointmentDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
     private Status status; // e.g., "Scheduled", "Completed", "Canceled"

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Doctor doctor;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Patient patient;
}
