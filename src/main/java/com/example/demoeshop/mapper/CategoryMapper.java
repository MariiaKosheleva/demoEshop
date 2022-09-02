package com.example.demoeshop.mapper;

import com.example.demoeshop.dto.CategoryDto;
import com.example.demoeshop.model.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {
    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    @InheritInverseConfiguration
    CategoryDto fromCategory(Category category);

    List<CategoryDto> fromCategoryList(List<Category> categories);
}
