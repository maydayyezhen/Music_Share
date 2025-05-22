package org.javaclimb.springbootmusic.model;

import java.util.Date;

public class CommentResponse {
    private Long id;
    private Integer userId;
    private String username;
    private String nickname;
    private String avatarUrl;
    private String contentType;
    private Long contentId;
    private String content;
    private Date createdAt;
    private Integer likeCount; // 点赞数
    private Boolean liked; // 当前用户是否点赞
<<<<<<< HEAD
=======

    private Long parentCommentId;

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }


>>>>>>> 7312840 (comment)
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public Long getContentId() {
        return contentId;
    }
    
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Integer getLikeCount() {
        return likeCount == null ? 0 : likeCount;
    }
    
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
    
    public Boolean getLiked() {
        return liked == null ? false : liked;
    }
    
    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
} 