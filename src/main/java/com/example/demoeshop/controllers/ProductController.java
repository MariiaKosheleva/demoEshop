package com.example.demoeshop.controllers;

import com.example.demoeshop.dto.CategoryDto;
import com.example.demoeshop.dto.ProductDto;
import com.example.demoeshop.model.Product;
import com.example.demoeshop.service.CategoryService;
import com.example.demoeshop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        List<ProductDto> products = productService.getAll();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "products";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String newProduct(Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categories);
        return "product";
    }

    @PostMapping("/new")
    public String saveProduct(ProductDto dto, Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        if (productService.save(dto)) {
            return "redirect:/products";
        } else {
            model.addAttribute("product", dto);
            model.addAttribute("categories", categories);
            return "product";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, Model model) {
        ProductDto product = productService.getById(id);
        model.addAttribute("product", product);
        return "productEditing";
    }

    @PostMapping("/edit/{id}")
    public String saveUpdatedProduct(Long id, ProductDto dto, Model model) {
        if ((dto.getTitle().isEmpty()) || (dto.getPrice() == null)) {
            model.addAttribute("product", dto);
            return "productEditing";
        }
        productService.edit(id, dto);
        return "redirect:/products";
    }

    @GetMapping("/{id}/basket")
    public String addProductToBasket(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/products";
        }
        productService.addProductToUserBasket(id, principal.getName());
        return "redirect:/products";
    }

    @PostMapping
    public ResponseEntity<Void> addProduct(ProductDto productDto) {
        productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping("/")
    public String getAllSortedProducts(@RequestParam String sort, Model model) {
        List<ProductDto> products = productService.getSortedProducts(sort);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAll());
        return "products";
    }

    @RequestMapping("/search")
    public String findProductByTitle(@RequestParam String title, Model model) {
        model.addAttribute("products", productService.getProductsByTitleContainingIgnoreCase(title));
        model.addAttribute("categories", categoryService.getAll());
        return "products";
    }

    @GetMapping("/productsByCategory/{id}")
    public String findProductByCategory(@PathVariable Long id, Model model) {
        List<Product> products = productService.getProductsByCategoryId(id);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAll());
        return "products";
    }
}
