package com.lifespring.repository;

import com.lifespring.model.RefreshToken;
import com.lifespring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken , UUID> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken );
    void deleteByUser(User user);

    Optional<RefreshToken> findByUser(User user);
}
