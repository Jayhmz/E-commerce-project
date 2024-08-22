package com.plantationhub.wesesta.authentication.service.registration;

import com.plantationhub.wesesta.authentication.client.EmailServiceClient;
import com.plantationhub.wesesta.authentication.dto.OnboardUserDTO;
import com.plantationhub.wesesta.authentication.enums.Roles;
import com.plantationhub.wesesta.authentication.model.AppUser;
import com.plantationhub.wesesta.authentication.service.password.TransactionPinService;
import com.plantationhub.wesesta.authentication.service.sms.TwilioSmsService;
import com.plantationhub.wesesta.authentication.service.token.TokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BasicRegistrationService implements OnboardNewUserService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TransactionPinService transactionPinService;
    private final TokenServiceImpl tokenService;
    private final TwilioSmsService twilioSmsService;
    private final EmailServiceClient emailServiceClient;

    @Autowired
    public BasicRegistrationService(UserService userService, PasswordEncoder passwordEncoder,
                                    TransactionPinService transactionPinService, TokenServiceImpl tokenService,
                                    TwilioSmsService twilioSmsService, EmailServiceClient emailServiceClient) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.transactionPinService = transactionPinService;
        this.tokenService = tokenService;
        this.twilioSmsService = twilioSmsService;
        this.emailServiceClient = emailServiceClient;
    }

    @Override
    public void onboardUser(OnboardUserDTO userDTO) {
        AppUser newUser = AppUser.builder()
                .firstName(userDTO.getFirstname())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .pin(transactionPinService.generatePin())
                .role(Roles.USER)
                .isActive(true)
                .build();
        userService.saveUser(newUser);
        generateToken(newUser);
    }

    private void generateToken(AppUser newUser) {
        var token = tokenService.generateToken();
        tokenService.saveMailToken(token, newUser);
        emailServiceClient.sendRegistrationTokenMail(newUser.getEmail(), token);
        log.info("token is --------> {}", token);
        log.info("token mail sent successfully...");
    }

    public void regenerateToken(String email){
        var token = tokenService.generateToken();
        tokenService.updateMailToken(token, email);
        emailServiceClient.sendRegistrationTokenMail(email, token);
        log.info("Mail token resent successfully : {}", token);
    }
}