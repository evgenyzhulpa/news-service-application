package com.example.REST.mapper;

import com.example.REST.dto.NewsListResponse;
import com.example.REST.dto.NewsResponse;
import com.example.REST.dto.NewsResponseWithCommentsCount;
import com.example.REST.dto.UpsertNewsRequest;
import com.example.REST.model.News;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public interface NewsMapper {
    News requestToNews(UpsertNewsRequest newsRequest);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest newsRequest);

    NewsResponse newsToResponse(News news);

    List<NewsResponseWithCommentsCount> newsListToResponseList(List<News> newsList);

    default NewsListResponse newsListToNewsListResponse(List<News> newsList) {
        NewsListResponse response = new NewsListResponse();
        response.setNews(newsListToResponseList(newsList));

        return response;
    }
}
