package org.javaclimb.springbootmusic.service;

import jakarta.persistence.EntityNotFoundException;
import org.javaclimb.springbootmusic.model.User;
import org.javaclimb.springbootmusic.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.javaclimb.springbootmusic.constants.FilePaths.USER_AVATAR_PATH;
import static org.javaclimb.springbootmusic.service.FileService.uploadFile;


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

    public User updateUser(Integer id, String nickname) {
        User user = getUserById(id);

        user.setNickname(nickname);
        return userRepository.save(user);
    }

    public User updatePassword(Integer userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public ResponseEntity<Void> uploadAvatarFile(Integer id, MultipartFile avatarFile) {
        User user = getUserById(id);
        user.setAvatarUrl(uploadFile(avatarFile, USER_AVATAR_PATH));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> deleteAvatarFileById(Integer id) {
        User user = getUserById(id);
        String fileUrl = user.getAvatarUrl();
        return FileService.deleteFile(fileUrl);
    }
}
