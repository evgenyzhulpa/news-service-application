package com.example.REST.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewsResponseWithCommentsCount {
    private Long id;
    private String title;
    private String description;
    private Integer commentsCount;
}
