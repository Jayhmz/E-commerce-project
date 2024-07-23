package com.plantationhub.wesesta.authentication.model;

import com.plantationhub.wesesta.authentication.enums.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "app_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(name = "firstname", nullable = false)
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "phone", unique = true, nullable = false)
    private String phone;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "pin", nullable = false)
    private String pin;
    @Column(nullable = false, columnDefinition = "ENUM('ADMIN', 'USER', 'CUSTOMER_CARE', 'AGENT')")
    @Enumerated(EnumType.STRING)
    private Roles role;
}
