package com.lifespring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reset_otp")
public class OTP {


    // creating the otp , which we will send to the user email address

    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 48)
    private UUID otpId;
    private UUID userId;
    private String otpCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;



}
