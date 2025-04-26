package org.javaclimb.springbootmusic.controller;
import org.javaclimb.springbootmusic.model.LoginRequest;
import org.javaclimb.springbootmusic.security.AuthResponse;
import org.javaclimb.springbootmusic.model.UserDetails;
import org.javaclimb.springbootmusic.service.UserService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
     public List<UserDetails> getAllUsers() {
         return userService.getAllUsers();
    }
    @GetMapping("/{name}")
    public UserDetails getUserByUserName(@PathVariable String name) {
        return userService.getUserByUserName(name);
    }

     @PostMapping
     public UserDetails createUser(@RequestBody UserDetails user) {
         return userService.createUser(user);
     }
     @DeleteMapping("/{name}")
     public void deleteUserById(@PathVariable String name) {
         userService.deleteUserByUserName(name);
     }
    @PutMapping("/{name}/password")
    public UserDetails updatePassword(
            @PathVariable String name,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        return userService.updatePassword(name, oldPassword, newPassword);
    }
    @PutMapping("/{name}/manage")
    public void updateRole(
            @PathVariable String name,
            @RequestParam String updateName,
            @RequestParam String role) {
        userService.updateRole(name, updateName, role);
    }
    @PutMapping("/{name}")
    public UserDetails updateUser(
            @PathVariable String name,
            @RequestParam String nickname) {
           return userService.updateUser(name, nickname);
    }

    //注册
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String name, @RequestParam String password) {
        return userService.register(name, password);
    }
    //登录
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        return userService.logout(authHeader);
    }


    @PostMapping("/{name}/avatarFile")
    public ResponseEntity<Void> uploadAvatarFile(@PathVariable String name, @RequestParam("avatarFile") MultipartFile avatarFile) {
        return userService.uploadAvatarFile(name,avatarFile);
    }

    @DeleteMapping("/{name}/avatarFile")
    public ResponseEntity<String> deleteAvatarFileById(@PathVariable String name) {
        return userService.deleteAvatarFileById(name);
    }
}
