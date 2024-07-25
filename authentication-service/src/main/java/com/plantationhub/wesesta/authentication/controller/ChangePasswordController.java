package com.plantationhub.wesesta.authentication.controller;

import com.plantationhub.wesesta.authentication.dto.ChangeOldPasswordDTO;
import com.plantationhub.wesesta.authentication.service.password.ChangePasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ChangePasswordController {

    private final ChangePasswordService changePasswordService;

    public ChangePasswordController(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> updatePassword(ChangeOldPasswordDTO changePasswordDTO){
        changePasswordService.changePassword(changePasswordDTO);
        return new ResponseEntity<>("Password updated successfully",HttpStatus.ACCEPTED);
    }
}
