package org.javaclimb.springbootmusic.repository;

import org.javaclimb.springbootmusic.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    
    // 根据评论ID和用户ID查找点赞记录
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Integer userId);
    
    // 根据评论ID和用户ID删除点赞记录
    void deleteByCommentIdAndUserId(Long commentId, Integer userId);
    
    // 根据评论ID统计点赞数
    long countByCommentId(Long commentId);
    
    // 检查用户是否点赞了评论
    boolean existsByCommentIdAndUserId(Long commentId, Integer userId);
    
    // 根据评论ID批量查询点赞数
    @Query("SELECT cl.commentId, COUNT(cl) FROM CommentLike cl WHERE cl.commentId IN ?1 GROUP BY cl.commentId")
    Iterable<Object[]> countLikesByCommentIds(Iterable<Long> commentIds);
} 