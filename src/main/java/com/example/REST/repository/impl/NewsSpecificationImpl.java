package com.example.REST.repository.impl;

import com.example.REST.dto.NewsFilter;
import com.example.REST.model.News;
import com.example.REST.repository.NewsSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class NewsSpecificationImpl implements NewsSpecification {

    @Override
    public Specification<News> withFilter(NewsFilter filter) {
        return Specification.where(byNewsCategoryId(filter.getNewsCategoryId()))
                .and(byUserId(filter.getUserId()));
    }

    private Specification<News> byNewsCategoryId(Long newsCategoryId) {
        return (root, query, criteriaBuilder) -> {
            if (newsCategoryId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("newsCategory").get("id"), newsCategoryId);
        };
    }

    private Specification<News> byUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("user").get("id"), userId);
        };
    }

}
