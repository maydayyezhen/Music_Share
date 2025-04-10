package org.javaclimb.springbootmusic.controller;
import org.javaclimb.springbootmusic.model.Artist;
import org.javaclimb.springbootmusic.model.User;
import org.javaclimb.springbootmusic.service.UserService;
import org.springframework.core.io.Resource;
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
     public void deleteUserById(@PathVariable int id) {
         userService.deleteUserById(id);
     }
    @PutMapping("/{id}/password")
    public void updatePassword(
            @PathVariable Integer id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.updatePassword(id, oldPassword, newPassword);
    }
    @GetMapping("/{id}/avatarFile")
    public ResponseEntity<Resource> getAvatarFileById(@PathVariable String name){
        return userService.getAvatarFileByName(name);
    }

    @PostMapping("/avatarFile")
    public String uploadAvatarFile(@RequestParam("avatarFile") MultipartFile avatarFile) {
        return userService.uploadAvatarFile(avatarFile);
    }


    @DeleteMapping("/{id}/avatarFile")
    public ResponseEntity<String> deleteAvatarFileById(@PathVariable("id") String name) {
        return userService.deleteAvatarFileById(name);
    }
}
