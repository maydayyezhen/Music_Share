package org.javaclimb.springbootmusic.controller;
import org.javaclimb.springbootmusic.model.User;
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
     public List<User> getAllUsers() {
         return userService.getAllUsers();
    }
    @GetMapping("/{name}")
    public User getUserByUserName(@PathVariable String name) {
        return userService.getUserByUserName(name);
    }

     @PostMapping
     public User createUser(@RequestBody User user) {
         return userService.createUser(user);
     }
     @DeleteMapping("/{name}")
     public void deleteUserById(@PathVariable String name) {
         userService.deleteUserByUserName(name);
     }
    @PutMapping("/{name}/password")
    public User updatePassword(
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
    public User updateUser(
            @PathVariable String name,
            @RequestParam String nickname) {
           return userService.updateUser(name, nickname);
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
