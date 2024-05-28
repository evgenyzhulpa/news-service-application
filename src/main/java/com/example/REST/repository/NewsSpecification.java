package com.example.REST.repository;

import com.example.REST.dto.NewsFilter;
import com.example.REST.model.News;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

    Specification<News> withFilter(NewsFilter filter);

}
