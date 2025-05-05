package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 根据内容类型和ID查找评论
    List<Comment> findByContentTypeAndContentIdOrderByCreatedAtDesc(String contentType, Long contentId);
    
    // 根据用户ID查找评论
    List<Comment> findByUserIdOrderByCreatedAtDesc(Integer userId);
    
    // 根据内容类型和ID删除评论
    void deleteByContentTypeAndContentId(String contentType, Long contentId);
} 