package com.example.demoeshop.service.impl;

import com.example.demoeshop.dto.ProductDto;
import com.example.demoeshop.mapper.ProductMapper;
import com.example.demoeshop.model.Basket;
import com.example.demoeshop.model.Product;
import com.example.demoeshop.model.User;
import com.example.demoeshop.repository.ProductRepository;
import com.example.demoeshop.service.BasketService;
import com.example.demoeshop.service.ProductService;
import com.example.demoeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper mapper = ProductMapper.MAPPER;
    private final BasketService basketService;
    private final ProductRepository productRepository;
    private final UserService userService;


    @Autowired
    public ProductServiceImpl(BasketService basketService, ProductRepository productRepository,
                              UserService userService) {
        this.basketService = basketService;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public boolean save(ProductDto productDto) {
        Product product = Product.builder()
                .image(productDto.getImage())
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .category(productDto.getCategory())
                .build();
        productRepository.save(product);
        return true;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void edit(Long productId, ProductDto productDto) {
        Product savedProduct = productRepository.getOne(productDto.getId());
        boolean changed = false;
        if (productDto.getPrice() != null) {
            savedProduct.setPrice(productDto.getPrice());
            changed = true;
        }
        if (productDto.getTitle() != null) {
            savedProduct.setTitle(productDto.getTitle());
            changed = true;
        }
        if (productDto.getCategory() != null) {
            savedProduct.setCategory(productDto.getCategory());
            changed = true;
        }
        if (changed) {
            productRepository.save(savedProduct);
        }
    }

    @Override
    public List<ProductDto> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    public void addProduct(ProductDto productDto) {
        Product product = mapper.toProduct(productDto);
        Product savedProduct = productRepository.save(product);
    }

    @Override
    public void addProductToUserBasket(Long productId, String username) {
        User user = userService.findByName(username);
        if (user == null) {
            throw new RuntimeException("User with name " + username + " not found");
        }
        Basket basket = user.getBasket();
        if (basket == null) {
            Basket newBasket = basketService.createBasket(user, Collections.singletonList(productId));
            user.setBasket(newBasket);
            userService.save(user);
        } else {
            basketService.addProduct(basket, Collections.singletonList(productId));
        }

    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id).orElse(new Product());
        return mapper.fromProduct(product);
    }

    @Override
    public List<ProductDto> getSortedProducts(String sort) {
        Sort sortedProductsByPrice = Sort.by("price");
        Sort sortedProductsByTitle = Sort.by("title");
        return switch (sort) {
            case "ascending" -> mapper.fromProductList(productRepository.findAll(sortedProductsByPrice.ascending()));
            case "descending" -> mapper.fromProductList(productRepository.findAll(sortedProductsByPrice.descending()));
            case "a-z" -> mapper.fromProductList(productRepository.findAll(sortedProductsByTitle));
            case "z-a" -> mapper.fromProductList(productRepository.findAll(sortedProductsByTitle.descending()));
            default -> mapper.fromProductList(productRepository.findAll());
        };
    }

    @Override
    public List<Product> getProductsByTitleContainingIgnoreCase(String title) {
        return productRepository.findProductByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findProductsByCategory_Id(categoryId);
    }
}
