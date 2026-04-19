package com.shoptestlab.controller;

import com.shoptestlab.dto.ProductDtos.CheckoutRequest;
import com.shoptestlab.model.Order;
import com.shoptestlab.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "4. Orders", description = "Checkout & order management (requires auth)")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    @Operation(summary = "Checkout cart and create order")
    public ResponseEntity<Order> checkout(@AuthenticationPrincipal UserDetails user,
                                           @Valid @RequestBody CheckoutRequest req) {
        return ResponseEntity.ok(orderService.checkout(user.getUsername(), req));
    }

    @GetMapping
    @Operation(summary = "Get all orders of logged-in user")
    public ResponseEntity<List<Order>> getMyOrders(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(orderService.getUserOrders(user.getUsername()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<Order> getOrder(@AuthenticationPrincipal UserDetails user,
                                           @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(user.getUsername(), id));
    }
}
