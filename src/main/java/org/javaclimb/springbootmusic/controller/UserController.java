package org.javaclimb.springbootmusic.controller;
import org.javaclimb.springbootmusic.model.User;
import org.javaclimb.springbootmusic.service.UserService;
import org.springframework.web.bind.annotation.*;

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
}
