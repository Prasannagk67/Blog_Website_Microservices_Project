package com.blog.controller;

import com.blog.dto.BlogDTO;
import com.blog.entity.Comment;
import com.blog.feignclint.BlogFeignClient;
import com.blog.serviceimpl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentServiceImpl commentServiceImpl;
    @Autowired
    BlogFeignClient blogFeignClient;
    @GetMapping("/blogs")
    public ResponseEntity<List<BlogDTO>> getAllBlogsFromBlogService() {
        List<BlogDTO> blogs = blogFeignClient.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }
    @GetMapping("/blog/{blogId}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable int blogId){
        return ResponseEntity.ok(blogFeignClient.getBlogById(blogId));
    }
    @PostMapping("/blog")
    public ResponseEntity<BlogDTO> createBlog(@RequestBody BlogDTO blogDTO){
        return ResponseEntity.ok(blogFeignClient.createBlog(blogDTO));
    }
    @DeleteMapping("/blog/{blogId}")
    public ResponseEntity<String> deleteBlog(@PathVariable int blogId){
        blogFeignClient.deleteBlog(blogId);
        return ResponseEntity.ok("Blog Deleted");
    }
    @PutMapping("/blog/{blogId}")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable int blogId,@RequestBody BlogDTO blogDTO){
        return ResponseEntity.ok(blogFeignClient.updateBlog(blogId,blogDTO));
    }
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment){
        return ResponseEntity.ok(commentServiceImpl.createComment(comment));
    }
    @GetMapping("/blogs/{blogId}")
    public ResponseEntity<List<Comment>> getCommentsByBlogId(@PathVariable int blogId){
        return ResponseEntity.ok(commentServiceImpl.getCommentsByBlogId(blogId));
    }
    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(){
        return ResponseEntity.ok(commentServiceImpl.getAllComments());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Comment>> getCommentById(@PathVariable int id){
        return ResponseEntity.ok(commentServiceImpl.getCommentById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable int id,@RequestBody Comment comment){
        return ResponseEntity.ok(commentServiceImpl.updateComment(id,comment));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id){
        commentServiceImpl.deleteComment(id);
        return ResponseEntity.ok("Comment Deleted");
    }

}
