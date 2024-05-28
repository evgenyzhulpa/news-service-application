package com.example.REST.controller;

import com.example.REST.dto.NewsFilter;
import com.example.REST.dto.NewsListResponse;
import com.example.REST.dto.NewsResponse;
import com.example.REST.dto.UpsertNewsRequest;
import com.example.REST.mapper.NewsMapper;
import com.example.REST.model.News;
import com.example.REST.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsService.findAll(filter)
                ));
    }

    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> filterBy(NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsService.filterBy(filter)
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                newsMapper.newsToResponse(
                        newsService.findById(id)
                ));
    }

    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News newNews = newsService.save(newsMapper.requestToNews(request));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponse(newNews));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable Long id,
                                               @RequestBody @Valid UpsertNewsRequest request) {

        News news = newsService.update(newsMapper.requestToNews(id, request));
        return ResponseEntity.ok(newsMapper.newsToResponse(news));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
