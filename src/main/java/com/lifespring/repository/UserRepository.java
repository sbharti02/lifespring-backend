package com.lifespring.repository;

import com.lifespring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User , UUID> {

    @Query("select u from User u where u.username = ?1 or u.email = ?2")
    Optional<User> findByUsernameOrEmail(String username, String email);

//    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

}
