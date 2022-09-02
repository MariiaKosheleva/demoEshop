package com.example.demoeshop.service.impl;

import com.example.demoeshop.dto.CategoryDto;
import com.example.demoeshop.mapper.CategoryMapper;
import com.example.demoeshop.repository.CategoryRepository;
import com.example.demoeshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper mapper = CategoryMapper.MAPPER;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getAll() {
        return mapper.fromCategoryList(categoryRepository.findAll());
    }
}
