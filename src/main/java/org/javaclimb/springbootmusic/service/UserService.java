package org.javaclimb.springbootmusic.service;

import org.javaclimb.springbootmusic.model.UserEntity;
import org.javaclimb.springbootmusic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.javaclimb.springbootmusic.constants.FilePaths.PORT_PATH;
import static org.javaclimb.springbootmusic.constants.FilePaths.USER_AVATAR_PATH;
import static org.javaclimb.springbootmusic.service.FileService.uploadFile;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserByUserName(String name) {
        return userRepository.findByUsername(name);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }
    public void deleteUserByUserName(String name) {
        userRepository.delete(getUserByUserName(name));
    }

    public UserEntity updateUser(String name, String nickname) {
        UserEntity user = getUserByUserName(name);

        user.setNickname(nickname);
        return userRepository.save(user);
    }

    public UserEntity updatePassword(String name, String oldPassword, String newPassword) {
        UserEntity user = getUserByUserName(name);
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
    public void updateRole(String name,String upDateName,String role) {
        UserEntity user = getUserByUserName(name);
        if(user.getRole().trim().equals("admin")){
            UserEntity updateUser =getUserByUserName(upDateName);
            if(role.trim().equals("admin")||role.trim().equals("user")) {
                updateUser.setRole(role);
                userRepository.save(updateUser);
            }
        }
    }
    public ResponseEntity<Void> uploadAvatarFile(String name, MultipartFile avatarFile) {
        UserEntity user = getUserByUserName(name);

        user.setAvatarUrl(PORT_PATH+uploadFile(avatarFile, USER_AVATAR_PATH));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> deleteAvatarFileById(String name) {
        UserEntity user = getUserByUserName(name);
        String fileUrl = user.getAvatarUrl();
        user.setAvatarUrl("");
        return FileService.deleteFile(fileUrl);
    }

    public ResponseEntity<String> login(String name, String password) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("密码不能为空");
        }
        if (!userRepository.existsByUsername(name)) {
            return ResponseEntity.badRequest().body("该用户名不存在");
        }
        UserEntity user = userRepository.findByUsername(name);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.badRequest().body("密码错误");
        }
        return ResponseEntity.ok("登录成功");
    }
    //注册
    public ResponseEntity<String> register(String name, String password) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("密码不能为空");
        }
        if (userRepository.existsByUsername(name)) {
            return ResponseEntity.badRequest().body("该用户名已被注册");
        }
        String encodedPassword = passwordEncoder.encode(password);
        UserEntity user = new UserEntity();
        user.setUsername(name);
        user.setPassword(encodedPassword);
        user.setRole("user");
        user.setNickname(name);
        userRepository.save(user);
        return ResponseEntity.ok("注册成功");
    }

}
