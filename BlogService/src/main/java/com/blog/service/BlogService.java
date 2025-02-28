package com.blog.service;

import com.blog.entiry.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    Blog createBlog(Blog blog);
    List<Blog> getAllBlogs();
    Optional<Blog> getBlogById(int id);
    Blog updateBlog(int id,Blog blog);
    void deleteBlog(int id);
}
