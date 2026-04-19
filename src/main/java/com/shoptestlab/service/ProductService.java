package com.shoptestlab.service;

import com.shoptestlab.dto.ProductDtos.ProductRequest;
import com.shoptestlab.exception.ResourceNotFoundException;
import com.shoptestlab.model.Product;
import com.shoptestlab.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product create(ProductRequest req) {
        Product product = Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .stock(req.getStock())
                .category(req.getCategory())
                .imageUrl(req.getImageUrl())
                .build();
        return productRepository.save(product);
    }

    public Product update(Long id, ProductRequest req) {
        Product product = getById(id);
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());
        product.setCategory(req.getCategory());
        product.setImageUrl(req.getImageUrl());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = getById(id);
        productRepository.delete(product);
    }
}
