package org.javaclimb.springbootmusic.service;

import org.javaclimb.springbootmusic.model.Comment;
import org.javaclimb.springbootmusic.model.CommentRequest;
import org.javaclimb.springbootmusic.model.CommentResponse;

import java.util.List;

public interface CommentService {
    // 添加评论
    Comment addComment(Integer userId, CommentRequest request);
    
    // 获取评论列表
    List<CommentResponse> getCommentsByContentTypeAndId(String contentType, Long contentId);
    
    // 根据用户ID查询获取评论列表
    List<CommentResponse> getCommentsByContentTypeAndId(String contentType, Long contentId, Integer currentUserId);
    
    // 获取用户的评论
    List<CommentResponse> getCommentsByUserId(Integer userId);
    
    // 删除评论
    boolean deleteComment(Long commentId, Integer userId);
    
    // 删除对象的所有评论
    void deleteCommentsByContentTypeAndId(String contentType, Long contentId);
    
    // 点赞评论
    void likeComment(Long commentId, Integer userId);
    
    // 取消点赞评论
    void unlikeComment(Long commentId, Integer userId);
    
    // 检查用户是否点赞了评论
    boolean hasUserLikedComment(Long commentId, Integer userId);
    
    // 获取评论的点赞数
    int getCommentLikeCount(Long commentId);
} 