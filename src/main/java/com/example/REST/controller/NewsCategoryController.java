package com.example.REST.controller;

import com.example.REST.dto.NewsCategoryFilter;
import com.example.REST.dto.NewsCategoryListResponse;
import com.example.REST.dto.NewsCategoryResponse;
import com.example.REST.dto.UpsertNewsCategoryRequest;
import com.example.REST.mapper.NewsCategoryMapper;
import com.example.REST.model.NewsCategory;
import com.example.REST.service.NewsCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/news_categories")
@RequiredArgsConstructor
public class NewsCategoryController {
    private final NewsCategoryService newsCategoryService;
    private final NewsCategoryMapper newsCategoryMapper;

    @GetMapping
    public ResponseEntity<NewsCategoryListResponse> findAll(NewsCategoryFilter filter) {
        return ResponseEntity.ok(
                newsCategoryMapper.newsCategoryListToNewsCategoryListResponse(
                        newsCategoryService.findAll(filter)
                        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                newsCategoryMapper.newsCategoryToNewsCategoryResponse(
                        newsCategoryService.findById(id)
                ));
    }

    @PostMapping
    public ResponseEntity<NewsCategoryResponse> create(@RequestBody @Valid UpsertNewsCategoryRequest request) {
        NewsCategory newNewsCategory = newsCategoryService.save(newsCategoryMapper.requestToNewsCategory(request));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newsCategoryMapper.newsCategoryToNewsCategoryResponse(newNewsCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsCategoryResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertNewsCategoryRequest request) {
        NewsCategory newsCategory = newsCategoryService.update(newsCategoryMapper.requestToNewsCategory(id, request));
        return ResponseEntity.ok(newsCategoryMapper.newsCategoryToNewsCategoryResponse(newsCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsCategoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
