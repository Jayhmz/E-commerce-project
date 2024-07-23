package com.plantationhub.wesesta.authentication.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "user_tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private Long tokenId;

    @Column(name = "tk_phone")
    private String phoneToken;

    @Column(name = "isPhoneVerified", columnDefinition = "BIT DEFAULT b'0'")
    private boolean isPhoneVerified;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tk_phone_created_at")
    private LocalDateTime phoneTokenCreatedAt;

    @Column(name = "tk_email")
    private String mailToken;

    @Column(name = "isEmailVerified", columnDefinition = "BIT DEFAULT b'0'")
    private boolean isEmailVerified;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tk_email_created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime mailTokenCreatedAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private AppUser user;


}
