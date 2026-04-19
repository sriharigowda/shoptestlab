package com.shoptestlab.controller;

import com.shoptestlab.dto.ProductDtos.ProductRequest;
import com.shoptestlab.model.Product;
import com.shoptestlab.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "5. Admin", description = "Admin-only product management (ROLE_ADMIN)")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final ProductService productService;

    @PostMapping("/products")
    @Operation(summary = "Create a product (admin)")
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(productService.create(req));
    }

    @PutMapping("/products/{id}")
    @Operation(summary = "Update a product (admin)")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(productService.update(id, req));
    }

    @DeleteMapping("/products/{id}")
    @Operation(summary = "Delete a product (admin)")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Product deleted"));
    }
}
