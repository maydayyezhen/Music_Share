package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
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
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("用户未找到: " + id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    // 更新用户信息
    public User updateUser(Integer id, String nickname) {
        User user = getUserById(id);

        user.setNickname(nickname);
        return userRepository.save(user);
    }

    // 更新密码
    public User updatePassword(Integer userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);

        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public ResponseEntity<Resource> getAvatarFileByName(Integer id){
        User user = getUserById(id);
        String fileName = user.getAvatarUrl();
        Path storageDir = Paths.get("users/avatar");
        return FileService.getFile(fileName,storageDir);
    }


    public String uploadAvatarFile(MultipartFile avatarFile) {
        Path uploadDir = Paths.get("users/avatar");
        return FileService.uploadFile(avatarFile, uploadDir);
    }


    public ResponseEntity<String> deleteAvatarFileById(Integer id) {
        User user = getUserById(id);
        String fileName = user.getAvatarUrl();
        Path storageDir = Paths.get("users/avatar");
        return FileService.deleteFile(fileName, storageDir);
    }
}
