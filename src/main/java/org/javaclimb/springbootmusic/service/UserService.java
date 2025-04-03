package org.javaclimb.springbootmusic.service;

import org.javaclimb.springbootmusic.model.User;
import org.javaclimb.springbootmusic.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

}
