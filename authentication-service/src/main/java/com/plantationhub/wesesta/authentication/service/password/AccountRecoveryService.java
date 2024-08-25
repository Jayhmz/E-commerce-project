package com.plantationhub.wesesta.authentication.service.password;

import com.plantationhub.wesesta.authentication.client.EmailServiceClient;
import com.plantationhub.wesesta.authentication.dto.ChangePasswordDTO;
import com.plantationhub.wesesta.authentication.dto.ResendMailTokenDTO;
import com.plantationhub.wesesta.authentication.service.registration.BasicRegistrationService;
import com.plantationhub.wesesta.authentication.service.registration.UserService;
import com.plantationhub.wesesta.authentication.service.token.TokenService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountRecoveryService {
    private final BasicRegistrationService onboardUserService;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceClient emailServiceClient;

    public AccountRecoveryService(BasicRegistrationService onboardUserService, UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder, EmailServiceClient emailServiceClient) {
        this.onboardUserService = onboardUserService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.emailServiceClient = emailServiceClient;
    }

    @Transactional
    public void sendAccountRecoveryMail(ResendMailTokenDTO resendMailTokenDTO){
        var user = userService.findByMail(resendMailTokenDTO.getEmail())
                .orElseThrow(()-> new BadCredentialsException("Unknown email address"));
        user.setActive(false);
        userService.saveUser(user);
        onboardUserService.recoverPasswordToken(user.getEmail());
    }

    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDTO){
        var user = userService.findByMail(changePasswordDTO.getEmail())
                .orElseThrow(()-> new BadCredentialsException("Unknown email address"));
        tokenService.updateMailToken(Integer.parseInt(changePasswordDTO.getToken()), user.getEmail());
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        userService.saveUser(user);
        emailServiceClient.sendChangePasswordAlertMail(user.getEmail(), user.getFirstName());
    }
}
