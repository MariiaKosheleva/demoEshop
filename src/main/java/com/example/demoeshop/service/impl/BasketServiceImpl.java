package com.example.demoeshop.service.impl;

import com.example.demoeshop.dto.BasketDetailDto;
import com.example.demoeshop.dto.BasketDto;
import com.example.demoeshop.model.*;
import com.example.demoeshop.repository.BasketRepository;
import com.example.demoeshop.repository.ProductRepository;
import com.example.demoeshop.service.BasketService;
import com.example.demoeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Autowired
    public BasketServiceImpl(BasketRepository basketRepository, ProductRepository productRepository,
                             UserService userService) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public Basket createBasket(User user, List<Long> products) {
        Basket basket = new Basket();
        basket.setUser(user);
        List<Product> productsInBasket = getProducts(products);
        basket.setProducts(productsInBasket);
        return basketRepository.save(basket);
    }

    private List<Product> getProducts(List<Long> products) {
        return products.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    public void addProduct(Basket basket, List<Long> products) {
        List<Product> productsInBasket = basket.getProducts();
        List<Product> newProductsInBasket = productsInBasket == null ? new ArrayList<>()
                : new ArrayList<>(productsInBasket);
        newProductsInBasket.addAll(getProducts(products));
        basket.setProducts(newProductsInBasket);
        basketRepository.save(basket);
    }

    @Override
    public void removeOneUnitOfProduct(Long productId, String username) {
        Basket basket = getUserBasket(username);
        basket.getProducts().remove(productRepository.getOne(productId));
        basketRepository.save(basket);
    }

    @Override
    public void removeAllUnitsOfProduct(Long productId, String username) {
        Basket basket = getUserBasket(username);
        basket.getProducts().removeAll(productRepository.findOneTypeOfProductById(productId));
        basketRepository.save(basket);
    }

    @Override
    public void clearBasket(String username) {
        Basket basket = getUserBasket(username);
        basket.getProducts().clear();
        basketRepository.save(basket);
    }

    private Basket getUserBasket(String username) {
        User user = getUserByUsername(username);
        return user.getBasket();
    }

    @Override
    public BasketDto getBasketByUser(String username) {
        User user = getUserByUsername(username);
        if (user == null || user.getBasket() == null) {
            return new BasketDto();
        }

        BasketDto basketDto = new BasketDto();
        Map<Long, BasketDetailDto> details = new HashMap<>();
        List<Product> products = user.getBasket().getProducts();
        for (Product product : products) {
            BasketDetailDto basketDetailDto = details.get(product.getId());
            if (basketDetailDto == null) {
                details.put(product.getId(), new BasketDetailDto(product));
            } else {
                basketDetailDto.setAmount(basketDetailDto.getAmount() + 1.0);
                basketDetailDto.setSum(basketDetailDto.getSum() + product.getPrice());
            }
        }
        basketDto.setBasketDetails(new ArrayList<>(details.values()));
        basketDto.aggregate();
        return basketDto;
    }

    private User getUserByUsername(String username) {
        return userService.findByName(username);
    }
}
