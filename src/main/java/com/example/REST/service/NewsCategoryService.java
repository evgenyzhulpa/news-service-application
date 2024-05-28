package com.example.REST.service;

import com.example.REST.dto.NewsCategoryFilter;
import com.example.REST.model.NewsCategory;

import java.util.List;

public interface NewsCategoryService {
    List<NewsCategory> findAll(NewsCategoryFilter filter);
    NewsCategory findById(Long id);
    NewsCategory save(NewsCategory newsCategory);
    NewsCategory update(NewsCategory newsCategory);
    void deleteById(Long id);
}
