package com.example.REST.repository;

import com.example.REST.dto.NewsCategoryFilter;
import com.example.REST.model.NewsCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long> {

    @EntityGraph(attributePaths = {"news"})
    Page<NewsCategory> findAll(Pageable pageable);
    @EntityGraph(attributePaths = {"news"})
    Optional<NewsCategory> findById(Long id);
}
