package com.plantationhub.wesesta.authentication.controller;

import com.plantationhub.wesesta.authentication.client.EmailServiceClient;
import com.plantationhub.wesesta.authentication.dto.ChangeOldPasswordDTO;
import com.plantationhub.wesesta.authentication.model.AppUser;
import com.plantationhub.wesesta.authentication.service.password.ChangePasswordService;
import com.plantationhub.wesesta.authentication.util.AppUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ChangePasswordController {

    private final ChangePasswordService changePasswordService;
    private final EmailServiceClient emailServiceClient;

    public ChangePasswordController(ChangePasswordService changePasswordService, EmailServiceClient emailServiceClient) {
        this.changePasswordService = changePasswordService;
        this.emailServiceClient = emailServiceClient;
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> updatePassword(ChangeOldPasswordDTO changePasswordDTO){
        changePasswordService.changePassword(changePasswordDTO);
        AppUser authenticatedUser = AppUtil.getAuthenticatedUser();
        emailServiceClient.sendChangePasswordAlert(authenticatedUser.getEmail(), authenticatedUser.getFirstName());
        return new ResponseEntity<>("Password updated successfully",HttpStatus.ACCEPTED);
    }
}
