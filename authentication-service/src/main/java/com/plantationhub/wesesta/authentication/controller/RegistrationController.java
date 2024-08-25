package com.plantationhub.wesesta.authentication.controller;

import com.plantationhub.wesesta.authentication.dto.OnboardUserDTO;
import com.plantationhub.wesesta.authentication.dto.ResendMailTokenDTO;
import com.plantationhub.wesesta.authentication.service.registration.BasicRegistrationService;
import com.plantationhub.wesesta.authentication.service.registration.OnboardNewUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {
    private final BasicRegistrationService onboardNewUserService;

    @Autowired
    public RegistrationController(BasicRegistrationService onboardNewUserService) {
        this.onboardNewUserService = onboardNewUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> onboardUser(@Valid @RequestBody OnboardUserDTO onboardUserDTO){
        onboardNewUserService.onboardUser(onboardUserDTO);
        return new ResponseEntity<>("Registration successful", HttpStatus.CREATED);
    }
    @PostMapping("/resend-mail-token")
    public ResponseEntity<?> resendVerifyMail(@Valid @RequestBody ResendMailTokenDTO resendMailTokenDTO){
        onboardNewUserService.regenerateToken(resendMailTokenDTO.getEmail());
        return new ResponseEntity<>("Token sent successfully.", HttpStatus.OK);
    }
}
