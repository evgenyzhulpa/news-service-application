package com.example.REST.mapper;

import com.example.REST.dto.NewsListResponse;
import com.example.REST.dto.NewsResponseWithCommentsCount;
import com.example.REST.dto.UpsertNewsRequest;
import com.example.REST.model.News;
import com.example.REST.service.NewsCategoryService;
import com.example.REST.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private NewsCategoryService newsCategoryService;

    @Override
    public News requestToNews(UpsertNewsRequest newsRequest) {
        News news = new News();
        news.setTitle(newsRequest.getTitle());
        news.setDescription(newsRequest.getDescription());
        news.setUser(userService.findById(newsRequest.getUserId()));
        news.setNewsCategory(newsCategoryService.findById(newsRequest.getNewsCategoryId()));

        return news;
    }

    @Override
    public News requestToNews(Long newsId, UpsertNewsRequest newsRequest) {
        News news = requestToNews(newsRequest);
        news.setId(newsId);
        return news;
    }

    @Override
    public NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        NewsListResponse listResponse = new NewsListResponse();
        List<NewsResponseWithCommentsCount> newsResponseWithCommentsCounts = newsList.stream()
                .map(news -> {
                    NewsResponseWithCommentsCount newsResponse = new NewsResponseWithCommentsCount();
                    newsResponse.setId(news.getId());
                    newsResponse.setTitle(news.getTitle());
                    newsResponse.setDescription(news.getDescription());
                    newsResponse.setCommentsCount(news.getComments().size());

                    return newsResponse;
                })
                .toList();
        listResponse.setNews(newsResponseWithCommentsCounts);

        return listResponse;
    }
}
