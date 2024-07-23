package com.plantationhub.wesesta.authentication.service.token;

import com.plantationhub.wesesta.authentication.model.AppUser;
import com.plantationhub.wesesta.authentication.model.Token;
import com.plantationhub.wesesta.authentication.repository.TokenRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public int generateToken() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

    @Override
    @Transactional
    public void savePhoneToken(int phoneToken, AppUser user) {
        Token token = Token.builder()
                .phoneToken(passwordEncoder.encode(String.valueOf(phoneToken)))
                .phoneTokenCreatedAt(LocalDateTime.now())
                .user(user)
                .build();
        tokenRepository.save(token);
        log.info("Phone Token id : {} persisted successfully", token.getTokenId() + " - " + phoneToken);
    }

    @Override
    @Transactional
    public void saveMailToken(int mailToken, AppUser user) {
        Token token = Token.builder()
                .mailToken(passwordEncoder.encode(String.valueOf(mailToken)))
                .mailTokenCreatedAt(LocalDateTime.now())
                .user(user)
                .build();
        tokenRepository.save(token);
        log.info("Mail Token id : {} persisted successfully", token.getTokenId() + " - " + mailToken);
    }

    @Override
    @Transactional
    public void verifyMailToken(Token token) {
        tokenRepository.verifyEmail(token.getTokenId());
        log.info("Email verified successfully");
    }
    @Override
    @Transactional
    public void verifyPhoneToken(Token token) {
        tokenRepository.save(token);
        log.info("Phone verified successfully");
    }

    @Override
    public void updateMailToken(int token, String email) {
        var tokenResponse = tokenRepository.findByUserMail(email);
        tokenRepository.updateEmailToken((Long) tokenResponse.get("token_id"), passwordEncoder.encode(String.valueOf(token)));
    }

    @Override
    public Map<String, Object> findByUserMail(String email) {
        var userTokenDetails = tokenRepository.findByUserMail(email);
        log.info("Token repository data : {}", userTokenDetails.values());
        return userTokenDetails;
    }


}
