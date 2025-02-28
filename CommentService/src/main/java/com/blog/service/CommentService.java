package com.blog.service;

import com.blog.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Comment comment);
    List<Comment> getAllComments();
    Optional<Comment> getCommentById(int id);
    Comment updateComment(int id, Comment comment);
    void deleteComment(int id);
    List<Comment> getCommentsByBlogId(int blogId);
}
