package org.javaclimb.springbootmusic.model;

public class CommentRequest {
    private String contentType; // 评论对象类型：song, album, artist, playlist
    private Long contentId;     // 评论对象ID
    private String content;     // 评论内容
<<<<<<< HEAD
=======

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    private Long parentCommentId; //被评论内容的ID
>>>>>>> 7312840 (comment)
    
    // Getters and Setters
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
} 