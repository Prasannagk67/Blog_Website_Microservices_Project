package com.blog.controller;

import com.blog.entity.User;
import com.blog.serviceimpl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    RestTemplate restTemplate;
    private static final String BLOG_SERVICE = "blogServiceCircuitBreaker";
    private static final String BLOG_RATE_LIMITER = "blogRateLimiter";
    @GetMapping("/blogs")
    @CircuitBreaker(name = BLOG_SERVICE, fallbackMethod = "fallbackForGetAllBlogs")
    @Retry(name = BLOG_SERVICE, fallbackMethod = "fallbackForGetAllBlogs")
    public ResponseEntity<Object> getAllBlogs(){
        String blogServiceUrl = "http://BLOG-SERVICE/blog";
        Object blogs = restTemplate.getForObject(blogServiceUrl,Object.class);
        return ResponseEntity.ok(blogs);
    }
    public ResponseEntity<Object> fallbackForGetAllBlogs(Throwable ex) {
        return ResponseEntity.ok("Blog Service is currently unavailable. Please try again later.");
    }
    @GetMapping("/blog/comments/{blogId}")
    @RateLimiter(name = BLOG_RATE_LIMITER, fallbackMethod = "fallbackForRateLimiter")
    public ResponseEntity<Object> getBlogAndComments(@PathVariable int blogId){
        String blogAndComment = "http://BLOG-SERVICE/blog/comments/"+blogId;
        Object blogAndComments = restTemplate.getForObject(blogAndComment,Object.class);
        return ResponseEntity.ok(blogAndComments);
    }
    public ResponseEntity<Object> fallbackForRateLimiter(int blogId, Throwable ex) {
        return ResponseEntity.status(429).body("Too many requests! Please try again later.");
    }
    @GetMapping("/blog/{id}")
    public ResponseEntity<Object> getBlogById(@PathVariable int id){
        String blogServiceUrl = "http://BLOG-SERVICE/blog/{id}";
        Object blog = restTemplate.getForObject(blogServiceUrl,Object.class,id);
        return ResponseEntity.ok(blog);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userServiceImpl.creatUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userServiceImpl.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable int id){
        return ResponseEntity.ok(userServiceImpl.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id,@RequestBody User user){
        return  ResponseEntity.ok(userServiceImpl.updateUser(id,user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        userServiceImpl.deleteUserById(id);
        return ResponseEntity.ok("User Deleted with Id :"+id);
    }
}
