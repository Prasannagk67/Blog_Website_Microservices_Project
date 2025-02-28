package com.blog.dto;

public class CommentDTO {
    private int id;
    private int userId;
    private int blogId;
    private String comment;
    private String commentedDate;
    private UserDTO userDTO;

    public CommentDTO() {
    }

    public CommentDTO(int id, int userId, int blogId, String comment, String commentedDate, UserDTO userDTO) {
        this.id = id;
        this.userId = userId;
        this.blogId = blogId;
        this.comment = comment;
        this.commentedDate = commentedDate;
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBlogId() {
        return blogId;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentedDate() {
        return commentedDate;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCommentedDate(String commentedDate) {
        this.commentedDate = commentedDate;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", blogId=" + blogId +
                ", comment='" + comment + '\'' +
                ", commentedDate='" + commentedDate + '\'' +
                '}';
    }
}
