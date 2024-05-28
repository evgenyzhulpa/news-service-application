package com.example.REST.mapper;

import com.example.REST.dto.NewsCategoryListResponse;
import com.example.REST.dto.NewsCategoryResponse;
import com.example.REST.dto.UpsertNewsCategoryRequest;
import com.example.REST.model.NewsCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class})
public interface NewsCategoryMapper {

    NewsCategory requestToNewsCategory(UpsertNewsCategoryRequest upsertNewsCategoryRequest);

    @Mapping(source = "newsCategoryId", target = "id")
    NewsCategory requestToNewsCategory(Long newsCategoryId, UpsertNewsCategoryRequest upsertNewsCategoryRequest);

    NewsCategoryResponse newsCategoryToNewsCategoryResponse(NewsCategory newsCategory);

    List<NewsCategoryResponse> newsCategoryListToNewsCategoryResponseList(List<NewsCategory> newsCategories);

    default NewsCategoryListResponse newsCategoryListToNewsCategoryListResponse(List<NewsCategory> newsCategories) {
        NewsCategoryListResponse response = new NewsCategoryListResponse();
        response.setNewsCategories(newsCategoryListToNewsCategoryResponseList(newsCategories));

        return response;
    }
}
