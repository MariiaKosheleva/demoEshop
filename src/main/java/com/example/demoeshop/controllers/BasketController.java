package com.example.demoeshop.controllers;

import com.example.demoeshop.dto.BasketDto;
import com.example.demoeshop.service.BasketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/basket")
    public String getBasket(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("basket", new BasketDto());
        } else {
            BasketDto basketDto = basketService.getBasketByUser(principal.getName());
            model.addAttribute("basket", basketDto);
        }
        return "basket";
    }

    @GetMapping("/basket/removeOneUnitOfProduct/{id}")
    public String removeOneUnitOfProductFromUserBasket(@PathVariable Long id, Principal principal) {
        basketService.removeOneUnitOfProduct(id, principal.getName());
        return "redirect:/basket";
    }

    @GetMapping("/basket/removeAllUnitsOfProduct/{id}")
    public String removeAllUnitsOfProductFromUserBasket(@PathVariable Long id, Principal principal) {
        basketService.removeAllUnitsOfProduct(id, principal.getName());
        return "redirect:/basket";
    }

    @GetMapping("/basket/clear")
    public String clearUserBasket(Principal principal) {
        basketService.clearBasket(principal.getName());
        return "redirect:/basket";
    }
}
