package com.shoptestlab.service;

import com.shoptestlab.dto.ProductDtos.CartRequest;
import com.shoptestlab.exception.BadRequestException;
import com.shoptestlab.exception.ResourceNotFoundException;
import com.shoptestlab.model.*;
import com.shoptestlab.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public List<CartItem> getCart(String username) {
        User user = findUser(username);
        return cartRepo.findByUser(user);
    }

    public CartItem addToCart(String username, CartRequest req) {
        User user = findUser(username);
        Product product = productRepo.findById(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStock() < req.getQuantity())
            throw new BadRequestException("Insufficient stock. Available: " + product.getStock());

        return cartRepo.findByUserIdAndProductId(user.getId(), product.getId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + req.getQuantity());
                    return cartRepo.save(existing);
                })
                .orElseGet(() -> cartRepo.save(CartItem.builder()
                        .user(user).product(product).quantity(req.getQuantity()).build()));
    }

    public CartItem updateQuantity(String username, Long cartItemId, Integer quantity) {
        if (quantity < 1) throw new BadRequestException("Quantity must be at least 1");
        CartItem item = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getUser().getUsername().equals(username))
            throw new BadRequestException("This cart item does not belong to you");

        item.setQuantity(quantity);
        return cartRepo.save(item);
    }

    public void removeFromCart(String username, Long cartItemId) {
        CartItem item = cartRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!item.getUser().getUsername().equals(username))
            throw new BadRequestException("This cart item does not belong to you");

        cartRepo.delete(item);
    }

    @Transactional
    public void clearCart(String username) {
        User user = findUser(username);
        cartRepo.deleteByUserId(user.getId());
    }

    private User findUser(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
