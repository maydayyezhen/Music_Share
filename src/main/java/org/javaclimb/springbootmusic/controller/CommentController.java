package org.javaclimb.springbootmusic.controller;

import org.javaclimb.springbootmusic.model.Comment;
import org.javaclimb.springbootmusic.model.CommentRequest;
import org.javaclimb.springbootmusic.model.CommentResponse;
import org.javaclimb.springbootmusic.model.User;
import org.javaclimb.springbootmusic.service.CommentService;
import org.javaclimb.springbootmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @Autowired
    private UserService userService;

    // 添加评论
    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUserName(username);
        
        Comment comment = commentService.addComment(currentUser.getId(), request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "评论添加成功");
        response.put("data", comment);
        
        return ResponseEntity.ok(response);
    }

    // 获取某个内容的评论列表
    @GetMapping("/{contentType}/{contentId}")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable String contentType,
            @PathVariable Long contentId) {
        
        // 获取当前登录用户ID，可能为空（未登录用户）
        Integer currentUserId = null;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
                String username = authentication.getName();
                User currentUser = userService.getUserByUserName(username);
                currentUserId = currentUser.getId();
            }
        } catch (Exception e) {
            // 用户未登录，忽略异常，currentUserId保持为null
        }
        
        List<CommentResponse> comments = commentService.getCommentsByContentTypeAndId(contentType, contentId, currentUserId);
        return ResponseEntity.ok(comments);
    }

    // 获取当前用户的评论
    @GetMapping("/my-comments")
    public ResponseEntity<List<CommentResponse>> getMyComments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUserName(username);
        
        List<CommentResponse> comments = commentService.getCommentsByUserId(currentUser.getId());
        return ResponseEntity.ok(comments);
    }

    // 删除评论
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUserName(username);
        
        boolean deleted = commentService.deleteComment(commentId, currentUser.getId());
        
        Map<String, Object> response = new HashMap<>();
        if (deleted) {
            response.put("success", true);
            response.put("message", "评论删除成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "评论不存在或无权删除");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    // 管理员删除内容下的所有评论
    @DeleteMapping("/admin/{contentType}/{contentId}")
    public ResponseEntity<?> deleteContentComments(
            @PathVariable String contentType,
            @PathVariable Long contentId) {
        
        // 检查是否为管理员
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUserName(username);
        
        if (!"admin".equals(currentUser.getRole())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "非管理员无权执行此操作");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        
        commentService.deleteCommentsByContentTypeAndId(contentType, contentId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "所有评论删除成功");
        
        return ResponseEntity.ok(response);
    }
    
    // 点赞评论
    @PostMapping("/{commentId}/like")
    public ResponseEntity<?> likeComment(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUserName(username);
        
        commentService.likeComment(commentId, currentUser.getId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "点赞成功");
        response.put("likeCount", commentService.getCommentLikeCount(commentId));
        
        return ResponseEntity.ok(response);
    }
    
    // 取消点赞评论
    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<?> unlikeComment(@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getUserByUserName(username);
        
        commentService.unlikeComment(commentId, currentUser.getId());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "取消点赞成功");
        response.put("likeCount", commentService.getCommentLikeCount(commentId));
        
        return ResponseEntity.ok(response);
    }
} 