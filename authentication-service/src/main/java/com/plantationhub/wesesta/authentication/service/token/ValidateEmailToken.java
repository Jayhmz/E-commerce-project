package com.plantationhub.wesesta.authentication.service.token;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.plantationhub.wesesta.authentication.client.EmailServiceClient;
import com.plantationhub.wesesta.authentication.enums.Roles;
import com.plantationhub.wesesta.authentication.jwt.JwtUserDetails;
import com.plantationhub.wesesta.authentication.jwt.JwtUtil;
import com.plantationhub.wesesta.authentication.model.Token;
import com.plantationhub.wesesta.authentication.service.registration.UserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ValidateEmailToken implements ValidateTokenService {
    @Value("${secret.key}")
    private String key;

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    private final EmailServiceClient emailServiceClient;

    @Autowired
    public ValidateEmailToken(TokenService tokenService, PasswordEncoder passwordEncoder, EmailServiceClient emailServiceClient) {
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.emailServiceClient = emailServiceClient;
    }

    /**
     * the token service findByUserPhone() returns both the token details and user details as map<String, Object>
     * loop through the map to get the objects for Token and AppUser
     **/
    private Map<String, Object> getTokenResult(String email) {
        log.info("inside validate token - getTokenResult");
        return tokenService.findByUserMail(email);
    }

    @Override
    public String validateToken(String mailToken, String email) {
        var tokenResponse = getTokenResult(email);
        var token = isTokenActive(tokenResponse);
        var tokenMatch = isTokenMatch(mailToken, token.getMailToken());
        if (tokenMatch) {
            tokenService.verifyMailToken(token);
            var authentication = new UsernamePasswordAuthenticationToken(
                    tokenResponse.get("email").toString(),
                    tokenResponse.get("password").toString(),
                    Collections.singleton(new SimpleGrantedAuthority(
                            Roles.valueOf((String) tokenResponse.get("role")).name())));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //send registration complete successfully mail here...
            emailServiceClient.sendOnboardSuccessMail(email);
            return new JwtUtil().generatePhoneJwtToken(getJwtDetails(tokenResponse), key);
        }else {
            throw new BadCredentialsException("Oops, unauthorized credentials. not found");
        }
    }

    /**
     * check for the expiration of the token
     **/
    private boolean isInTime(Token token) {
        LocalDateTime now = LocalDateTime.now();
        var createdAt = token.getMailTokenCreatedAt();
        return createdAt.isAfter(now.minusSeconds(300));
    }

    private Token isTokenActive(Map<String, Object> tokenResponse) {
        Token token = Token.builder()
                .tokenId(Long.parseLong(tokenResponse.get("token_id").toString()))
                .mailToken(tokenResponse.get("tk_email").toString())
                .mailTokenCreatedAt(LocalDateTime.parse(tokenResponse.get("tk_email_created_at").toString(), DATE_TIME_FORMATTER))
                .build();
        if (!isInTime(token)) {
            throw new TokenExpiredException("Token Expired!", Instant.now());
        }
        return token;
    }

    /**
     * Check if token matches with the raw token.
     **/
    private boolean isTokenMatch(String rawToken, String encodedToken) {
        var matches = passwordEncoder.matches(rawToken, encodedToken);
        if (!matches) {
            throw new BadCredentialsException("Unauthorized credentials");
        }
        return true;
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
