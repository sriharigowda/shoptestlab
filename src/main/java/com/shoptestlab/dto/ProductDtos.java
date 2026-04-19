package com.shoptestlab.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

public class ProductDtos {

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class ProductRequest {
        @NotBlank
        private String name;
        private String description;

        @NotNull @DecimalMin("0.01")
        private BigDecimal price;

        @NotNull @Min(0)
        private Integer stock;

        private String category;
        private String imageUrl;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class CartRequest {
        @NotNull
        private Long productId;

        @NotNull @Min(1)
        private Integer quantity;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class CheckoutRequest {
        @NotBlank
        private String shippingAddress;
    }
}
