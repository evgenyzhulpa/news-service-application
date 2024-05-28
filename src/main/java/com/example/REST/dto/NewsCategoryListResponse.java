package com.example.REST.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsCategoryListResponse {
    private List<NewsCategoryResponse> newsCategories = new ArrayList<>();
}
