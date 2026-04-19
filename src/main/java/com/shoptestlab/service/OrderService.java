package com.shoptestlab.service;

import com.shoptestlab.dto.ProductDtos.CheckoutRequest;
import com.shoptestlab.exception.BadRequestException;
import com.shoptestlab.exception.ResourceNotFoundException;
import com.shoptestlab.model.*;
import com.shoptestlab.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartItemRepository cartRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    @Transactional
    public Order checkout(String username, CheckoutRequest req) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<CartItem> cartItems = cartRepo.findByUser(user);
        if (cartItems.isEmpty()) throw new BadRequestException("Cart is empty");

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem ci : cartItems) {
            Product product = ci.getProduct();
            if (product.getStock() < ci.getQuantity())
                throw new BadRequestException("Insufficient stock for " + product.getName());

            // reduce stock
            product.setStock(product.getStock() - ci.getQuantity());
            productRepo.save(product);

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity()));
            total = total.add(itemTotal);

            orderItems.add(OrderItem.builder()
                    .product(product)
                    .quantity(ci.getQuantity())
                    .price(product.getPrice())
                    .build());
        }

        Order order = Order.builder()
                .user(user)
                .totalAmount(total)
                .status(Order.OrderStatus.CONFIRMED)
                .shippingAddress(req.getShippingAddress())
                .build();

        orderItems.forEach(oi -> oi.setOrder(order));
        order.setOrderItems(orderItems);

        Order saved = orderRepo.save(order);
        cartRepo.deleteByUserId(user.getId());

        return saved;
    }

    public List<Order> getUserOrders(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderRepo.findByUserIdOrderByCreatedAtDesc(user.getId());
    }

    public Order getOrder(String username, Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if (!order.getUser().getUsername().equals(username))
            throw new BadRequestException("This order does not belong to you");
        return order;
    }
}
