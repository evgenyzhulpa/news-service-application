package com.example.REST.service.impl;

import com.example.REST.aop.CheckingNewsEditingByUser;
import com.example.REST.dto.NewsFilter;
import com.example.REST.exception.EntityNotFoundException;
import com.example.REST.model.News;
import com.example.REST.model.NewsCategory;
import com.example.REST.model.User;
import com.example.REST.repository.NewsRepository;
import com.example.REST.repository.NewsSpecification;
import com.example.REST.service.NewsCategoryService;
import com.example.REST.service.NewsService;
import com.example.REST.service.UserService;
import com.example.REST.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserService userService;
    private final NewsCategoryService newsCategoryService;
    private final NewsSpecification newsSpecification;

    @Override
    public List<News> findAll(NewsFilter filter) {
        return newsRepository.findAll(
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent();
    }

    @Override
    public List<News> filterBy(NewsFilter filter) {
        return newsRepository.findAll(newsSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(
                                "Новость с id {0} не найдена!", id
                        )));
    }

    @Override
    public News save(News news) {
        User user = userService.findById(news.getUser().getId());
        NewsCategory newsCategory = newsCategoryService.findById(news.getNewsCategory().getId());
        news.setUser(user);
        news.setNewsCategory(newsCategory);
        return newsRepository.save(news);
    }

    @Override
    @CheckingNewsEditingByUser
    public News update(News news) {
        User user = userService.findById(news.getUser().getId());
        NewsCategory newsCategory = newsCategoryService.findById(news.getNewsCategory().getId());
        News existedNews = findById(news.getId());

        BeanUtils.copyNonNullProperties(news, existedNews);
        existedNews.setUser(user);
        existedNews.setNewsCategory(newsCategory);
        return newsRepository.save(existedNews);
    }

    @Override
    @CheckingNewsEditingByUser
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
