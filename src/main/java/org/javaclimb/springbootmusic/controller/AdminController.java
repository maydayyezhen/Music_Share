package org.javaclimb.springbootmusic.controller;

import org.javaclimb.springbootmusic.model.Admin;
import org.javaclimb.springbootmusic.repository.AdminRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin("*")
public class AdminController {
    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    @GetMapping("/{username}")
    public Admin getAdminByUsername(@PathVariable String username) {
        return adminRepository.findByUsername(username);
    }
}
