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

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

     @PostMapping
     public User createUser(@RequestBody User user) {
         return userService.createUser(user);
     }
     @DeleteMapping("/{id}")
     public void deleteUserById(@PathVariable Integer id) {
         userService.deleteUserById(id);
     }
    @PutMapping("/{id}/password")
    public User updatePassword(
            @PathVariable Integer id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        return userService.updatePassword(id, oldPassword, newPassword);
    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Integer id,
            @RequestParam String nickname) {
           return userService.updateUser(id, nickname);
    }

    @PostMapping("/{id}/avatarFile")
    public ResponseEntity<Void> uploadAvatarFile(@PathVariable Integer id, @RequestParam("avatarFile") MultipartFile avatarFile) {
        return userService.uploadAvatarFile(id,avatarFile);
    }

    @DeleteMapping("/{id}/avatarFile")
    public ResponseEntity<String> deleteAvatarFileById(@PathVariable("id") Integer id) {
        return userService.deleteAvatarFileById(id);
    }
}
