package com.example.REST.dto;

import lombok.Data;
import java.util.List;

@Data
public class NewsResponse {
    private Long id;
    private String title;
    private String description;
    private List<CommentResponse> comments;
}
