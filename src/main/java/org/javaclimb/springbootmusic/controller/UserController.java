package org.javaclimb.springbootmusic.controller;
import jakarta.annotation.security.RolesAllowed;
import org.javaclimb.springbootmusic.model.LoginRequest;
import org.javaclimb.springbootmusic.model.User;
import org.javaclimb.springbootmusic.security.AuthResponse;
import org.javaclimb.springbootmusic.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 【只有管理员能查看所有用户】
    @RolesAllowed({"ADMIN"})
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 【普通用户和管理员都可以拿自己的信息】
    @GetMapping("/{token}")
    public User getUserByToken(@PathVariable String token) {
        return userService.getUserByToken(token);
    }

    // 【注册时允许匿名（未登录）也可以访问】
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // 【只有管理员才能删除用户】
    @RolesAllowed({"ADMIN"})
    @DeleteMapping("/{name}")
    public void deleteUserById(@PathVariable String name) {
        userService.deleteUserByUserName(name);
    }

    // 【自己可以改自己的密码，管理员也可以改别人的】
    @PreAuthorize("hasRole('ADMIN') or #name == authentication.name")
    @PutMapping("/{name}/password")
    public User updatePassword(
            @PathVariable String name,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        return userService.updatePassword(name, oldPassword, newPassword);
    }

    // 【只有管理员可以改别人的角色】
    @RolesAllowed({"ADMIN"})
    @PutMapping("/{name}/manage")
    public void updateRole(
            @PathVariable String name,
            @RequestParam String updateName,
            @RequestParam String role) {
        userService.updateRole(name, updateName, role);
    }

    // 【自己或管理员可以修改用户信息（比如昵称）】
    @PreAuthorize("hasRole('ADMIN') or #name == authentication.name")
    @PutMapping("/{name}")
    public User updateUser(
            @PathVariable String name,
            @RequestParam String nickname) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("在JwtFilter中认证完成：authentication.name = " + authentication.getName());
        return userService.updateUser(name, nickname);
    }

    // 【注册接口，允许匿名访问】
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String name, @RequestParam String password) {
        return userService.register(name, password);
    }

    // 【登录接口，允许匿名访问】
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    // 【注销接口，登录用户才可以访问】
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        return userService.logout(authHeader);
    }

    // 【上传头像：自己或管理员可以上传】
    @PreAuthorize("hasRole('ADMIN') or #name == authentication.name")
    @PostMapping("/{name}/avatarFile")
    public ResponseEntity<Void> uploadAvatarFile(@PathVariable String name, @RequestParam("avatarFile") MultipartFile avatarFile) {
        return userService.uploadAvatarFile(name, avatarFile);
    }

    // 【删除头像：自己或管理员可以删除】
    @PreAuthorize("hasRole('ADMIN') or #name == authentication.name")
    @DeleteMapping("/{name}/avatarFile")
    public ResponseEntity<String> deleteAvatarFileById(@PathVariable String name) {
        return userService.deleteAvatarFileById(name);
    }
}

