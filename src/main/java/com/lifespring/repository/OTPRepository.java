package com.lifespring.repository;

import com.lifespring.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OTPRepository extends JpaRepository<OTP,UUID> {

    Optional<OTP > findByUserId(UUID id);
}
