package com.blog.dto;

import com.blog.entiry.Blog;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
public class BlogAndComment {
    private Blog blog;
    private List<CommentDTO> comment;

    public BlogAndComment() {
    }

    public BlogAndComment(Blog blog, List<CommentDTO> comment) {
        this.blog = blog;
        this.comment = comment;
    }

    public Blog getBlog() {
        return blog;
    }

    public List<CommentDTO> getComment() {
        return comment;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public void setComment(List<CommentDTO> comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "BlogAndComment{" +
                "blog=" + blog +
                ", comment=" + comment +
                '}';
    }
}
