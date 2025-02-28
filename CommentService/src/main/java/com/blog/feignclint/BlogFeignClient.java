package com.blog.feignclint;

import com.blog.dto.BlogDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "BLOG-SERVICE")
public interface BlogFeignClient {
    @GetMapping("/blog")
    List<BlogDTO> getAllBlogs();

    @GetMapping("/blog/{blogId}")
    BlogDTO getBlogById(@PathVariable int blogId);

    @PostMapping("/blog")
    BlogDTO createBlog(@RequestBody BlogDTO blogDTO);

    @DeleteMapping("/blog/{blogId}")
    Void deleteBlog(@PathVariable int blogId);

    @PutMapping("/blog/{blogId}")
    BlogDTO updateBlog(@PathVariable int blogId,@RequestBody BlogDTO blogDTO);
}

