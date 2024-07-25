package com.plantationhub.wesesta.authentication.controller;

import com.plantationhub.wesesta.authentication.dto.ChangePasswordDTO;
import com.plantationhub.wesesta.authentication.dto.ResendMailTokenDTO;
import com.plantationhub.wesesta.authentication.service.password.AccountRecoveryService;
import com.plantationhub.wesesta.authentication.service.registration.BasicRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AccountRecoveryController {

    private final AccountRecoveryService accountRecoveryService;

    @Autowired
    public AccountRecoveryController(AccountRecoveryService accountRecoveryService) {
        this.accountRecoveryService = accountRecoveryService;
    }

    @PostMapping("/send-forgot-password-token")
    public ResponseEntity<?> resendVerifyMail(@Valid @RequestBody ResendMailTokenDTO resendMailTokenDTO){
        accountRecoveryService.sendAccountRecoveryMail(resendMailTokenDTO);
        return new ResponseEntity<>("Token sent to "+resendMailTokenDTO.getEmail()+"", HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO){
        accountRecoveryService.changePassword(changePasswordDTO);
        return new ResponseEntity<>("Account modified successfully", HttpStatus.ACCEPTED);
    }

}
