package com.shoptestlab.controller;

import com.shoptestlab.dto.ProductDtos.CartRequest;
import com.shoptestlab.model.CartItem;
import com.shoptestlab.service.CartService;
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
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "3. Cart", description = "Shopping cart (requires auth)")
@SecurityRequirement(name = "bearerAuth")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "Get current user's cart")
    public ResponseEntity<List<CartItem>> getCart(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(cartService.getCart(user.getUsername()));
    }

    @PostMapping
    @Operation(summary = "Add item to cart")
    public ResponseEntity<CartItem> addToCart(@AuthenticationPrincipal UserDetails user,
                                               @Valid @RequestBody CartRequest req) {
        return ResponseEntity.ok(cartService.addToCart(user.getUsername(), req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update cart item quantity")
    public ResponseEntity<CartItem> updateQuantity(@AuthenticationPrincipal UserDetails user,
                                                    @PathVariable Long id,
                                                    @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(cartService.updateQuantity(user.getUsername(), id, body.get("quantity")));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove item from cart")
    public ResponseEntity<Map<String, String>> remove(@AuthenticationPrincipal UserDetails user,
                                                       @PathVariable Long id) {
        cartService.removeFromCart(user.getUsername(), id);
        return ResponseEntity.ok(Map.of("message", "Item removed from cart"));
    }

    @DeleteMapping
    @Operation(summary = "Clear entire cart")
    public ResponseEntity<Map<String, String>> clear(@AuthenticationPrincipal UserDetails user) {
        cartService.clearCart(user.getUsername());
        return ResponseEntity.ok(Map.of("message", "Cart cleared"));
    }
}
