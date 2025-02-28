package com.blog.serviceimpl;

import com.blog.entiry.Blog;
import com.blog.repository.BlogRepository;
import com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Optional<Blog> getBlogById(int id) {
        return blogRepository.findById(id);
    }

    @Override
    public Blog updateBlog(int id, Blog blog) {
        return blogRepository.findById(id).map(updatedBlog -> {
            updatedBlog.setName(blog.getName());
            updatedBlog.setLink(blog.getLink());
            return blogRepository.save(updatedBlog);
        }).orElseThrow(() -> new RuntimeException("Blog Not Found with :"+id));
    }

    @Override
    public void deleteBlog(int id) {
        blogRepository.deleteById(id);
    }
}
