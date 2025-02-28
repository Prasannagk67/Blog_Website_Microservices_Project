package com.blog.controller;

import com.blog.dto.BlogAndComment;
import com.blog.dto.CommentDTO;
import com.blog.dto.UserDTO;
import com.blog.entiry.Blog;
import com.blog.serviceimpl.BlogServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogServiceImpl blogServiceImpl;
    @Autowired
    RestTemplate restTemplate;
    private static final String COMMENT_SERVICE = "commentServiceCircuitBreaker";
    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog){
        return ResponseEntity.ok(blogServiceImpl.createBlog(blog));
    }
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs(){
        return ResponseEntity.ok(blogServiceImpl.getAllBlogs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Blog>> getBlogById(@PathVariable int id){
        return ResponseEntity.ok(blogServiceImpl.getBlogById(id));
    }
    @GetMapping("/comments/{blogId}")
    @CircuitBreaker(name = COMMENT_SERVICE,fallbackMethod = "fallbackForComments")
    public ResponseEntity<BlogAndComment> getCommentsByBlogId(@PathVariable int blogId){
        Optional<Blog> blog = blogServiceImpl.getBlogById(blogId);
        if (blog.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        String commentServiceUrl = "http://COMMENT-SERVICE/comment/blogs/"+blogId;
        List<CommentDTO> comments =  restTemplate.getForObject(commentServiceUrl,List.class);
        BlogAndComment blogAndComment =    new BlogAndComment(blog.get(),comments);
        return ResponseEntity.ok(blogAndComment);
    }
    public ResponseEntity<BlogAndComment> fallbackForComments(int blogId, Throwable ex) {
        Optional<Blog> blog = blogServiceImpl.getBlogById(blogId);
        if (blog.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CommentDTO> emptyComments = Collections.emptyList();
        BlogAndComment blogAndComment = new BlogAndComment(blog.get(), emptyComments);
        return ResponseEntity.ok(blogAndComment);
    }


    @GetMapping("/comments/user/{blogId}")
    public ResponseEntity<BlogAndComment> getBlogWithCommentsAndUsers(@PathVariable int blogId) {
        Optional<Blog> blog = blogServiceImpl.getBlogById(blogId);
        if (blog.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        String commentServiceUrl = "http://COMMENT-SERVICE/comment/blogs/"+blogId;
        ResponseEntity<List<CommentDTO>> response = restTemplate.exchange(
                commentServiceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CommentDTO>>() {}
        );
        List<CommentDTO> comments = response.getBody();
        for (CommentDTO comment : comments) {
            if (comment.getUserId() != 0) {
                String userServiceUrl = "http://USER-SERVICE/user/" + comment.getUserId();
                UserDTO user = restTemplate.getForObject(userServiceUrl, UserDTO.class);
                comment.setUserDTO(user);
            }
        }
        BlogAndComment responseDTO = new BlogAndComment(blog.get(), comments);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable int id,@RequestBody Blog blog){
        return ResponseEntity.ok(blogServiceImpl.updateBlog(id,blog));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable int id){
        blogServiceImpl.deleteBlog(id);
        return ResponseEntity.ok("Blog Deleted");
    }
}
