package com.dennis.noxrental.controller;

import com.dennis.noxrental.entity.DTO.AdminRentalResponseDTO;
import com.dennis.noxrental.constant.AppConstants;
import com.dennis.noxrental.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, AdminRentalResponseDTO>> listRentals() {
        return ResponseEntity.ok().body(Map.of(AppConstants.API_DATA_RESPONSE_KEY, adminService.getAdminRentalSummary()));
    }

}
