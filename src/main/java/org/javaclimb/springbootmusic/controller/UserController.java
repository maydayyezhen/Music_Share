package org.javaclimb.springbootmusic.controller;
import org.javaclimb.springbootmusic.model.User;
import org.springframework.web.bind.annotation.*;
import org.javaclimb.springbootmusic.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
     @GetMapping
     public List<User> getAllUsers() {
         return userRepository.findAll();
    }
    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }
     @PostMapping
     public User createUser(@RequestBody User user) {
         return userRepository.save(user);
     }
     @DeleteMapping("/{id}")
     public void deleteUser(@PathVariable int id) {
         userRepository.deleteById(id);
     }
}
