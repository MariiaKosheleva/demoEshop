package com.example.demoeshop.service;

import com.example.demoeshop.dto.BasketDto;
import com.example.demoeshop.model.Basket;
import com.example.demoeshop.model.User;

import java.util.List;

public interface BasketService {
    Basket createBasket(User user, List<Long> products);

    void addProduct(Basket basket, List<Long> products);

    void removeOneUnitOfProduct(Long productId, String username);

    void removeAllUnitsOfProduct(Long productId, String username);

    void clearBasket(String username);

    BasketDto getBasketByUser(String username);
}
