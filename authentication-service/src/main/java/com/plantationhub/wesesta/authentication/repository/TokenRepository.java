package com.plantationhub.wesesta.authentication.repository;

import com.plantationhub.wesesta.authentication.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(nativeQuery = true,
            value = "SELECT tk.token_id, tk.tk_phone, tk.tk_phone_created_at, usr.firstname, usr.phone, usr.password, usr.role " +
                    "FROM user_tokens tk " +
                    "JOIN app_users usr " +
                    "ON tk.user_id = usr.user_id " +
                    "WHERE usr.phone = :userPhone")
    Map<String, Object> findByUserPhone(@Param("userPhone") String userPhone);
    @Query(nativeQuery = true,
            value = "SELECT tk.token_id, tk.tk_email, tk.tk_email_created_at, usr.firstname, usr.email, usr.password, usr.role " +
                    "FROM user_tokens tk " +
                    "JOIN app_users usr " +
                    "ON tk.user_id = usr.user_id " +
                    "WHERE usr.email = :userMail")
    Map<String, Object> findByUserMail(@Param("userMail") String userMail);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_tokens SET is_email_verified = 1 WHERE token_id = :tokenId", nativeQuery = true)
    void verifyEmail(@Param("tokenId") Long tokenId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_tokens SET tk_email = :newToken, " +
            "tk_email_created_at = CURRENT_TIMESTAMP WHERE token_id = :tokenId", nativeQuery = true)
    void updateEmailToken(@Param("tokenId") Long tokenId, @Param("newToken") String newToken);

}
