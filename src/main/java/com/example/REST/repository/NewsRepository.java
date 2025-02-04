package com.example.REST.repository;

import com.example.REST.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
    @EntityGraph(attributePaths = {"comments"})
    Page<News> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"comments"})
    Optional<News> findById(Long id);
}
