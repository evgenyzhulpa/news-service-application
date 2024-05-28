package com.example.REST.dto;

import lombok.Data;

import java.util.List;

@Data
public class NewsCategoryResponse {
    private Long id;
    private String name;
    private List<NewsResponse> news;
}
