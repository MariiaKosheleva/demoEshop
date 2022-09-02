package com.example.demoeshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {
    private long amountOfProducts;
    private double sum;
    private List<BasketDetailDto> basketDetails = new ArrayList<>();

    public void aggregate() {
        this.amountOfProducts = basketDetails.size();
        this.sum = basketDetails.stream()
                .map(BasketDetailDto::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
