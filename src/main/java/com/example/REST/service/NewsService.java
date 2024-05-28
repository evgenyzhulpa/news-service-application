package com.example.REST.service;

import com.example.REST.dto.NewsFilter;
import com.example.REST.model.News;

import java.util.List;

public interface NewsService {
    List<News> findAll(NewsFilter filter);
    List<News> filterBy(NewsFilter filter);
    News findById(Long id);
    News save(News news);
    News update(News news);
    void deleteById(Long id);
}
