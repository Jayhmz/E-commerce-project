package com.plantationhub.wesesta.authentication.repository;

import com.plantationhub.wesesta.authentication.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    @Query(value = "SELECT u FROM AppUser u WHERE u.isActive = true AND u.phone = :phoneOrEmail OR u.email = :phoneOrEmail")
    Optional<AppUser> findByPhoneOrEmail(@Param("phoneOrEmail") String phoneOrEmail);
    @Modifying
    @Query(value = "UPDATE AppUser u SET u.isActive = false WHERE u.email = :email")
    void deactivateUser(@Param("email") String email);
}
