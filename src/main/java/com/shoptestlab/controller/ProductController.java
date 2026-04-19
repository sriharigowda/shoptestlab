package com.shoptestlab.controller;

import com.shoptestlab.model.Product;
import com.shoptestlab.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "2. Products", description = "Product catalog (public)")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name")
    public ResponseEntity<List<Product>> search(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Filter products by category")
    public ResponseEntity<List<Product>> byCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getByCategory(category));
    }
}
