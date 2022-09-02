package com.example.demoeshop.dto;

import com.example.demoeshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketDetailDto {
    private String title;
    private Long productId;
    private String image;
    private Double price;
    private Double amount;
    private Double sum;

    public BasketDetailDto(Product product) {
        this.title = product.getTitle();
        this.productId = product.getId();
        this.image = product.getImage();
        this.price = product.getPrice();
        this.amount = 1.0;
        this.sum = product.getPrice();
    }
}
