package org.javaclimb.springbootmusic.service;

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

    public User getUserByUserName(String name) {
        return userRepository.findByUsername(name);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUserByUserName(String name) {
        userRepository.delete(getUserByUserName(name));
    }

    public User updateUser(String name, String nickname) {
        User user = getUserByUserName(name);

        user.setNickname(nickname);
        return userRepository.save(user);
    }


    public User updatePassword(String name, String oldPassword, String newPassword) {
        User user = getUserByUserName(name);
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
    public void updateRole(String name,String upDateName,String role) {
        User user = getUserByUserName(name);
        if(user.getRole().trim().equals("admin")){
            User updateUser =getUserByUserName(upDateName);
            if(role.trim().equals("admin")||role.trim().equals("user")) {
                updateUser.setRole(role);
                userRepository.save(updateUser);
            }
        }
    }
    public ResponseEntity<Void> uploadAvatarFile(String name, MultipartFile avatarFile) {
        User user = getUserByUserName(name);

        user.setAvatarUrl("http://localhost:8080/"+uploadFile(avatarFile, USER_AVATAR_PATH));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<String> deleteAvatarFileById(String name) {
        User user = getUserByUserName(name);
        String fileUrl = user.getAvatarUrl();
        user.setAvatarUrl("");
        return FileService.deleteFile(fileUrl);
    }
}
