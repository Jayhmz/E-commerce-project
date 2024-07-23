package com.plantationhub.wesesta.authentication.service.token;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.plantationhub.wesesta.authentication.enums.Roles;
import com.plantationhub.wesesta.authentication.jwt.JwtUserDetails;
import com.plantationhub.wesesta.authentication.jwt.JwtUtil;
import com.plantationhub.wesesta.authentication.model.Token;
import com.plantationhub.wesesta.authentication.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

@Service
public class ValidatePhoneToken implements ValidateTokenService {
    @Value("${secret.key}")
    private String key;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ValidatePhoneToken(TokenRepository tokenRepository, PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * the token repository findByUserPhone() returns both the token details and user details as map<String, Object>
     * loop through the map to get the objects for Token and AppUser
     **/
    private Map<String, Object> getTokenResult(String phoneNumber) {
        return tokenRepository.findByUserPhone(phoneNumber);
    }

    @Override
    public String validateToken(String generatedToken, String phoneNumber) {
        var tokenResponse = getTokenResult(phoneNumber);
        Token token = Token.builder()
                .tokenId(Long.parseLong(tokenResponse.get("token_id").toString()))
                .phoneToken(tokenResponse.get("tk_phone").toString())
                .phoneTokenCreatedAt(LocalDateTime.parse(tokenResponse.get("tk_phone_created_at").toString()))
                .build();
        if (!isActive(token)) {
            throw new TokenExpiredException("Token Expired!", Instant.now());
        }

        var matches = passwordEncoder.matches(generatedToken, token.getPhoneToken());
        if (!matches) {
            throw new BadCredentialsException("Unauthorized credentials");
        }
        System.out.println("Key is ------> {} " + key);
        var authentication = new UsernamePasswordAuthenticationToken(
                tokenResponse.get("phone").toString(),
                tokenResponse.get("password").toString(),
                Collections.singleton(new SimpleGrantedAuthority(
                        Roles.valueOf((String) tokenResponse.get("role")).name())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.isAuthenticated()) {
            return new JwtUtil().generatePhoneJwtToken(getJwtDetails(tokenResponse), key);
        } else {
            throw new BadCredentialsException("Incorrect username or password. not found");
        }
    }

    /**
     * check for the expiration of the token
     **/
    private boolean isActive(Token token) {
        LocalDateTime now = LocalDateTime.now();
        var createdAt = token.getPhoneTokenCreatedAt();
        return createdAt.isAfter(now.minusSeconds(600));
    }

    /**
     * JwtUserDetails to return AppUser object
     **/
    private JwtUserDetails getJwtDetails(Map<String, Object> tkMap) {
        return JwtUserDetails.builder()
                .firstname((String) tkMap.get("firstname"))
                .role((String) tkMap.get("role"))
                .build();

    }

}
