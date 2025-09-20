package com.lifespring.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.SQLType;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
public class RefreshToken {


    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 45)
    @Id
    private UUID refreshTokenId;

    private String refreshToken;

    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "usersid")
    private User user;




}
