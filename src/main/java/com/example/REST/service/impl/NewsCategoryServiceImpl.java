package com.example.REST.service.impl;

import com.example.REST.dto.NewsCategoryFilter;
import com.example.REST.exception.EntityNotFoundException;
import com.example.REST.model.NewsCategory;
import com.example.REST.repository.NewsCategoryRepository;
import com.example.REST.service.NewsCategoryService;
import com.example.REST.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsCategoryServiceImpl implements NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;

    @Override
    public List<NewsCategory> findAll(NewsCategoryFilter filter) {
        return newsCategoryRepository.findAll(
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent();
    }

    @Override
    public NewsCategory findById(Long id) {
        return newsCategoryRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(MessageFormat.format(
                                "Категория новостей с id {0} не найдена!", id
                        )));
    }

    @Override
    public NewsCategory save(NewsCategory newsCategory) {
        return newsCategoryRepository.save(newsCategory);
    }

    @Override
    public NewsCategory update(NewsCategory newsCategory) {
        NewsCategory existedNewsCategory = findById(newsCategory.getId());
        BeanUtils.copyNonNullProperties(newsCategory, existedNewsCategory);
        return newsCategoryRepository.save(existedNewsCategory);
    }

    @Override
    public void deleteById(Long id) {
        newsCategoryRepository.deleteById(id);
    }
}
