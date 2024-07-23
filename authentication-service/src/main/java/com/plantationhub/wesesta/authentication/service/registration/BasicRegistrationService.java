package com.plantationhub.wesesta.authentication.service.registration;

import com.plantationhub.wesesta.authentication.dto.OnboardUserDTO;
import com.plantationhub.wesesta.authentication.enums.Roles;
import com.plantationhub.wesesta.authentication.model.AppUser;
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
    private final PinGeneratorService pinGeneratorService;
    private final TokenServiceImpl tokenService;
    private final TwilioSmsService twilioSmsService;

    @Autowired
    public BasicRegistrationService(UserService userService, PasswordEncoder passwordEncoder,
                                    PinGeneratorService pinGeneratorService, TokenServiceImpl tokenService, TwilioSmsService twilioSmsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.pinGeneratorService = pinGeneratorService;
        this.tokenService = tokenService;
        this.twilioSmsService = twilioSmsService;
    }

    @Override
    public void onboardUser(OnboardUserDTO userDTO) {
        AppUser newUser = AppUser.builder()
                .firstName(userDTO.getFirstname())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .pin(pinGeneratorService.generatePin())
                .role(Roles.USER)
                .build();
        userService.saveUser(newUser);
        generateMailToken(newUser);
        //feign client for generating wallet comes here...
    }

    private void generatePhoneToken(AppUser newUser) {
        var token = tokenService.generateToken();
        tokenService.savePhoneToken(token, newUser);
        //create feign client for notification service
        twilioSmsService.sendTokenSms(token, newUser.getPhone());
    }

    private void generateMailToken(AppUser newUser) {
        var token = tokenService.generateToken();
       log.info("token is --------> " + token);
        tokenService.saveMailToken(token, newUser);
        //create feign client for notification service
        //create feign client for wallet service
    }

    public void regenerateMailToken(String email){
        var token = tokenService.generateToken();
        tokenService.updateMailToken(token, email);
        log.info("Mail token resent successfully : {}", token);
    }
}