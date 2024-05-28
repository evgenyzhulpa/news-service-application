package com.example.REST.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsListResponse {
    private List<NewsResponseWithCommentsCount> news = new ArrayList<>();
}
