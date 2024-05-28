package com.example.REST.service;

import com.example.REST.dto.CommentFilter;
import com.example.REST.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findByNewsId(CommentFilter filter);
    Comment findById(Long id);
    Comment save(Comment comment);
    Comment update(Comment comment);
    void deleteById(Long id);
}
