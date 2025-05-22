package org.javaclimb.springbootmusic.service.impl;

import org.javaclimb.springbootmusic.model.Comment;
import org.javaclimb.springbootmusic.model.CommentLike;
import org.javaclimb.springbootmusic.model.CommentRequest;
import org.javaclimb.springbootmusic.model.CommentResponse;
import org.javaclimb.springbootmusic.repository.CommentLikeRepository;
import org.javaclimb.springbootmusic.repository.CommentRepository;
import org.javaclimb.springbootmusic.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Override
    public Comment addComment(Integer userId, CommentRequest request) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setContentType(request.getContentType());
        comment.setContentId(request.getContentId());
        comment.setContent(request.getContent());
<<<<<<< HEAD
=======
        comment.setParentCommentId(request.getParentCommentId());
>>>>>>> 7312840 (comment)
        comment.setCreatedAt(new Date());
        
        return commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByContentTypeAndId(String contentType, Long contentId) {
        List<Comment> comments = commentRepository.findByContentTypeAndContentIdOrderByCreatedAtDesc(contentType, contentId);
        return convertToCommentResponses(comments, null);
    }
    
    @Override
    public List<CommentResponse> getCommentsByContentTypeAndId(String contentType, Long contentId, Integer currentUserId) {
        List<Comment> comments = commentRepository.findByContentTypeAndContentIdOrderByCreatedAtDesc(contentType, contentId);
        return convertToCommentResponses(comments, currentUserId);
    }

    @Override
    public List<CommentResponse> getCommentsByUserId(Integer userId) {
        List<Comment> comments = commentRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return convertToCommentResponses(comments, userId);
    }

    @Override
    @Transactional
    public boolean deleteComment(Long commentId, Integer userId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            // 验证是否是当前用户的评论
            if (comment.getUserId().equals(userId)) {
                commentRepository.deleteById(commentId);
                return true;
            }
        }
        
        return false;
    }

    @Override
    @Transactional
    public void deleteCommentsByContentTypeAndId(String contentType, Long contentId) {
        commentRepository.deleteByContentTypeAndContentId(contentType, contentId);
    }
    
    @Override
    @Transactional
    public void likeComment(Long commentId, Integer userId) {
        // 检查用户是否已经点赞了该评论
        if (!hasUserLikedComment(commentId, userId)) {
            CommentLike like = new CommentLike();
            like.setCommentId(commentId);
            like.setUserId(userId);
            like.setCreatedAt(new Date());
            commentLikeRepository.save(like);
        }
    }
    
    @Override
    @Transactional
    public void unlikeComment(Long commentId, Integer userId) {
        commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
    }
    
    @Override
    public boolean hasUserLikedComment(Long commentId, Integer userId) {
        return commentLikeRepository.existsByCommentIdAndUserId(commentId, userId);
    }
    
    @Override
    public int getCommentLikeCount(Long commentId) {
        return (int) commentLikeRepository.countByCommentId(commentId);
    }
    
    // 将Comment实体转换为CommentResponse
    private List<CommentResponse> convertToCommentResponses(List<Comment> comments, Integer currentUserId) {
        if (comments.isEmpty()) {
            return List.of();
        }
        
        // 提取所有评论的ID
        List<Long> commentIds = comments.stream()
                .map(Comment::getId)
                .collect(Collectors.toList());
        
        // 批量查询点赞数
        Map<Long, Long> likeCountMap = new HashMap<>();
        Iterable<Object[]> likeCounts = commentLikeRepository.countLikesByCommentIds(commentIds);
        StreamSupport.stream(likeCounts.spliterator(), false)
                .forEach(row -> likeCountMap.put((Long) row[0], (Long) row[1]));
        
        // 批量查询当前用户是否点赞
        Map<Long, Boolean> userLikedMap = new HashMap<>();
        if (currentUserId != null) {
            for (Long commentId : commentIds) {
                userLikedMap.put(commentId, commentLikeRepository.existsByCommentIdAndUserId(commentId, currentUserId));
            }
        }
        
        return comments.stream().map(comment -> {
            CommentResponse response = new CommentResponse();
            response.setId(comment.getId());
            response.setUserId(comment.getUserId());
            response.setContentType(comment.getContentType());
            response.setContentId(comment.getContentId());
            response.setContent(comment.getContent());
            response.setCreatedAt(comment.getCreatedAt());
<<<<<<< HEAD
=======
            response.setParentCommentId(comment.getParentCommentId());
>>>>>>> 7312840 (comment)
            
            // 设置点赞数
            Long likeCount = likeCountMap.getOrDefault(comment.getId(), 0L);
            response.setLikeCount(likeCount.intValue());
            
            // 设置当前用户是否点赞
            Boolean isLiked = userLikedMap.getOrDefault(comment.getId(), false);
            response.setLiked(isLiked);
            
            // 如果评论关联了用户信息，则设置用户相关字段
            if (comment.getUser() != null) {
                response.setUsername(comment.getUser().getUsername());
                response.setNickname(comment.getUser().getNickname());
                response.setAvatarUrl(comment.getUser().getAvatarUrl());
            }
            
            return response;
        }).collect(Collectors.toList());
    }
} 