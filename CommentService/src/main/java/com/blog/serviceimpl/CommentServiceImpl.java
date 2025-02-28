package com.blog.serviceimpl;

import com.blog.entity.Comment;
import com.blog.repository.CommentRepository;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> getCommentById(int id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment updateComment(int id, Comment comment) {
        return commentRepository.findById(id).map(updatedComment -> {
            updatedComment.setUserId(comment.getUserId());
            updatedComment.setBlogId(comment.getBlogId());
            updatedComment.setComment(comment.getComment());
            updatedComment.setCommentedDate(comment.getCommentedDate());
            return commentRepository.save(updatedComment);
        }).orElseThrow(() -> new RuntimeException("Comment Not Found"));
    }

    @Override
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getCommentsByBlogId(int blogId) {
        return commentRepository.getCommentsByBlogId(blogId);
    }
}
