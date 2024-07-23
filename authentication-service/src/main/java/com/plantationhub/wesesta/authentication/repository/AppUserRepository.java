package com.plantationhub.wesesta.authentication.repository;

import com.plantationhub.wesesta.authentication.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    @Query(value = "SELECT * FROM app_users u WHERE u.phone = :phoneOrEmail OR u.email = :phoneOrEmail", nativeQuery = true)
    Optional<AppUser> findByPhoneOrEmail(@Param("phoneOrEmail") String phoneOrEmail);
    Optional<AppUser> findByEmail(String email);
}
