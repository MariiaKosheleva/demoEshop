package com.example.demoeshop.service;

import com.example.demoeshop.dto.ProductDto;
import com.example.demoeshop.model.Product;

import java.util.List;

public interface ProductService {
    boolean save(ProductDto productDto);

    void save(Product product);

    void edit(Long productId, ProductDto productDto);

    List<ProductDto> getAll();

    void addProduct(ProductDto productDto);

    void addProductToUserBasket(Long productId, String username);

    ProductDto getById(Long id);

    List<ProductDto> getSortedProducts(String sort);

    List<Product> getProductsByTitleContainingIgnoreCase(String title);

    List<Product> getProductsByCategoryId(Long categoryId);

}

