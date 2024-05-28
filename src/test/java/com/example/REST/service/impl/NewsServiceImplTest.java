package com.example.REST.service.impl;

import com.example.REST.dto.NewsFilter;
import com.example.REST.model.News;
import com.example.REST.model.NewsCategory;
import com.example.REST.model.User;
import com.example.REST.repository.NewsRepository;
import com.example.REST.repository.NewsSpecification;
import com.example.REST.service.NewsCategoryService;
import com.example.REST.service.NewsService;
import com.example.REST.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NewsServiceImplTest {

    private final UserService userService =
            Mockito.mock(UserService.class);

    private final NewsCategoryService newsCategoryService =
            Mockito.mock(NewsCategoryService.class);

    private final NewsRepository newsRepository =
            Mockito.mock(NewsRepository.class);

    private final NewsSpecification newsSpecification = Mockito.mock(NewsSpecification.class);

    private final NewsService newsService = new NewsServiceImpl(newsRepository, userService,
            newsCategoryService, newsSpecification);

    private News news;

    @BeforeEach
    public void setUp() {
        Long newsId = 1l;

        User user = new User();
        user.setId(newsId);
        user.setName("Test user");
        user.setComments(List.of());

        NewsCategory newsCategory = new NewsCategory();
        newsCategory.setId(newsId);
        newsCategory.setName("Test news category");

        news = new News();
        news.setId(newsId);
        news.setUser(user);
        news.setNewsCategory(newsCategory);
        news.setDescription("Test news");
        news.setCreatedUp(LocalDateTime.now());
        news.setUpdatedUp(LocalDateTime.now());
        news.setComments(List.of());
        user.setNews(List.of(news));
        newsCategory.setNews(List.of(news));
    }

    @Test
    @DisplayName("Test findAll method")
    public void testFindAll() {
        NewsFilter newsFilter = new NewsFilter();
        newsFilter.setPageNumber(1);
        newsFilter.setPageSize(10);
        Pageable pageable = PageRequest.of(newsFilter.getPageNumber(), newsFilter.getPageSize());

        ArrayList<News> newsList = new ArrayList<>();
        newsList.add(news);
        when(newsRepository.findAll(pageable)).thenReturn(new PageImpl<>(newsList));
        Collection<News> newsFromService = newsService.findAll(newsFilter);

        assertEquals(newsList.size(), newsFromService.size());
        verify(newsRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test filterBy method")
    public void testFilterBy() {
        NewsFilter newsFilter = new NewsFilter();
        newsFilter.setPageNumber(1);
        newsFilter.setPageSize(10);
        newsFilter.setUserId(1l);
        newsFilter.setNewsCategoryId(1l);
        Pageable pageable = PageRequest.of(newsFilter.getPageNumber(), newsFilter.getPageSize());

        ArrayList<News> newsList = new ArrayList<>();
        newsList.add(news);

        when(newsSpecification.withFilter(newsFilter))
                .thenReturn(null);
        when(newsRepository.findAll(newsSpecification.withFilter(newsFilter), pageable))
                .thenReturn(new PageImpl<>(newsList));
        Collection<News> newsFromService = newsService.filterBy(newsFilter);

        assertEquals(newsList.size(), newsFromService.size());
        verify(newsRepository, times(1))
                .findAll(newsSpecification.withFilter(newsFilter), pageable);
    }


    @Test
    @DisplayName("Test findById method")
    public void testFindById() {
        Long newsId = news.getId();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));
        News newsFromService = newsService.findById(newsId);
        assertEquals(newsId, newsFromService.getId());
        verify(newsRepository, times(1)).findById(newsId);
    }

    @Test
    @DisplayName("Test save method")
    public void testSave() {
        newsService.save(news);
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    @DisplayName("Test update method")
    public void testUpdate() {
        Long newsId = news.getId();
        when(newsRepository.findById(newsId)).thenReturn(Optional.of(news));

        newsService.update(news);
        verify(newsRepository, times(1)).save(any(News.class));
    }

    @Test
    @DisplayName("Test delete by id method")
    public void testDeleteById() {
        newsService.deleteById(news.getId());
        verify(newsRepository, times(1)).deleteById(news.getId());
    }

}
