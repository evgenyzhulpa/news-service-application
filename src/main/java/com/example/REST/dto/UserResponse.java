package com.example.REST.dto;

import com.example.REST.model.Comment;
import com.example.REST.model.News;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private List<NewsResponse> news = new ArrayList<>();
    private List<CommentResponse> comments = new ArrayList<>();
}
