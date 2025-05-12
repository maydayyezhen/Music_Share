package org.javaclimb.springbootmusic.controller;
import org.javaclimb.springbootmusic.model.UserLike;
import org.javaclimb.springbootmusic.service.UserLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userLikes")
@CrossOrigin("*")
public class UserLikeController {
    public final UserLikeService userLikeService;
    public UserLikeController(UserLikeService userLikeService) {
        this.userLikeService = userLikeService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserLike>> getUserLikes(@PathVariable Integer userId) {
        List<UserLike> userLikes = userLikeService.getUserLikesByUserId(userId);
        return ResponseEntity.ok(userLikes);
    }


    @PostMapping
    public UserLike createUserLike(@RequestBody UserLike userLikes) {
        return userLikeService.createUserLike(userLikes);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserLike(
            @RequestParam Integer userId,
            @RequestParam String targetType,
            @RequestParam Integer targetId
    ) {
        userLikeService.deleteByUserAndTarget(userId, targetType, targetId);
        return ResponseEntity.ok("取消点赞成功");
    }


}
