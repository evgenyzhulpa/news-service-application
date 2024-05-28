package com.example.REST.service.impl;

import com.example.REST.dto.NewsCategoryFilter;
import com.example.REST.model.NewsCategory;
import com.example.REST.repository.NewsCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NewsCategoryImplTest {

    private final NewsCategoryRepository newsCategoryRepository =
            Mockito.mock(NewsCategoryRepository.class);
    private final NewsCategoryServiceImpl newsCategoryService = new NewsCategoryServiceImpl(newsCategoryRepository);
    private NewsCategory newsCategory;

    @BeforeEach
    public void setUp() {
        Long newsCategoryId = 1l;

        newsCategory = new NewsCategory();
        newsCategory.setId(newsCategoryId);
        newsCategory.setName("Test news category");
        newsCategory.setNews(List.of());
    }

    @Test
    @DisplayName("Test findAll method")
    public void testFindAll() {
        NewsCategoryFilter newsCategoryFilter = new NewsCategoryFilter();
        newsCategoryFilter.setPageNumber(1);
        newsCategoryFilter.setPageSize(10);
        Pageable pageable = PageRequest.of(newsCategoryFilter.getPageNumber(), newsCategoryFilter.getPageSize());

        ArrayList<NewsCategory> newsCategories = new ArrayList<>();
        newsCategories.add(newsCategory);
        when(newsCategoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(newsCategories));
        Collection<NewsCategory> newsCategoryFromService = newsCategoryService.findAll(newsCategoryFilter);

        assertEquals(newsCategories.size(), newsCategoryFromService.size());
        verify(newsCategoryRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findById method")
    public void testFindById() {
        Long newsCategoryId = newsCategory.getId();
        when(newsCategoryRepository.findById(newsCategoryId)).thenReturn(Optional.of(newsCategory));

        NewsCategory newsCategoryFromService = newsCategoryService.findById(newsCategoryId);
        assertEquals(newsCategoryId, newsCategoryFromService.getId());
        verify(newsCategoryRepository, times(1)).findById(newsCategoryId);
    }

    @Test
    @DisplayName("Test save method")
    public void testSave() {
        newsCategoryRepository.save(newsCategory);
        verify(newsCategoryRepository, times(1)).save(any(NewsCategory.class));
    }

    @Test
    @DisplayName("Test update method")
    public void testUpdate() {
        Long newsCategoryId = newsCategory.getId();
        when(newsCategoryRepository.findById(newsCategoryId)).thenReturn(Optional.of(newsCategory));

        newsCategoryService.update(newsCategory);
        verify(newsCategoryRepository, times(1)).save(any(NewsCategory.class));
    }

    @Test
    @DisplayName("Test delete by id method")
    public void testDeleteById() {
        newsCategoryService.deleteById(newsCategory.getId());
        verify(newsCategoryRepository, times(1)).deleteById(newsCategory.getId());
    }

}
