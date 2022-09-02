package com.example.demoeshop.repository;

import com.example.demoeshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findOneTypeOfProductById(Long id);

    List<Product> findProductByTitleContainingIgnoreCase(String title);

    List<Product> findProductsByCategory_Id(Long categoryId);


}
