package com.plantationhub.email.controller;

import com.plantationhub.email.service.SendChangePasswordMail;
import com.plantationhub.email.service.SendChangePasswordTokenMail;
import com.plantationhub.email.service.SendMail;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
@Slf4j
public class AccountRecoveryMailController {

    @Autowired
    private SendChangePasswordTokenMail sendChangePasswordTokenMail;
    @Autowired
    private SendChangePasswordMail sendChangePasswordMail;

    @PostMapping("/send-change-password-token-mail")
    public ResponseEntity<?> sendRecoverPasswordTokenMail(@RequestParam String email, @RequestParam String token) throws MessagingException {
        sendChangePasswordTokenMail.sendMail(email, token);
        return new ResponseEntity<>("New password token has been sent successfully",HttpStatus.OK);
    }

    @PostMapping("/send-password-change-successful-mail")
    public ResponseEntity<?> sendChangePasswordAlert(@RequestParam String email, @RequestParam String username) throws MessagingException {
        sendChangePasswordMail.sendMail(email, username);
        return new ResponseEntity<>("Password changed successfully",HttpStatus.OK);
    }

}
