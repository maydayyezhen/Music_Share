package org.javaclimb.springbootmusic.service;

import org.javaclimb.springbootmusic.model.User;
import org.javaclimb.springbootmusic.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    // 更新用户信息
    public User updateUser(Integer id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setNickname(userDetails.getNickname());
            // 不更新用户名和密码
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // 更新密码
    public void updatePassword(Integer userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public ResponseEntity<Resource> getAvatarFileByName(String name){
        User user = getUserByUsername(name);
        String fileName = user.getAvatarUrl();
        Path storageDir = Paths.get("users/avatar");
        return FileService.getFile(fileName,storageDir);
    }


    public String uploadAvatarFile(MultipartFile avatarFile) {
        Path uploadDir = Paths.get("users/avatar");
        return FileService.uploadFile(avatarFile, uploadDir);
    }


    public ResponseEntity<String> deleteAvatarFileById(String name) {
        User user = getUserByUsername(name);
        String fileName = user.getAvatarUrl();
        Path storageDir = Paths.get("users/avatar");
        return FileService.deleteFile(fileName, storageDir);
    }
}

