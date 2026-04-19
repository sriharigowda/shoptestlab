package com.shoptestlab.config;

import com.shoptestlab.model.Product;
import com.shoptestlab.model.User;
import com.shoptestlab.repository.ProductRepository;
import com.shoptestlab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        // create admin if not exists
        if (!userRepo.existsByUsername("admin")) {
            userRepo.save(User.builder()
                    .username("admin")
                    .email("admin@shoptestlab.com")
                    .password(encoder.encode("admin123"))
                    .fullName("System Administrator")
                    .role(User.Role.ADMIN)
                    .build());
            System.out.println(">>> Admin user created: admin / admin123");
        }

        // create test user
        if (!userRepo.existsByUsername("testuser")) {
            userRepo.save(User.builder()
                    .username("testuser")
                    .email("test@shoptestlab.com")
                    .password(encoder.encode("test123"))
                    .fullName("Test User")
                    .role(User.Role.USER)
                    .build());
            System.out.println(">>> Test user created: testuser / test123");
        }

        // seed products if empty
        if (productRepo.count() == 0) {
            List<Product> products = List.of(
                Product.builder().name("iPhone 15 Pro").description("Apple's flagship smartphone")
                        .price(new BigDecimal("120000")).stock(50).category("Electronics")
                        .imageUrl("https://via.placeholder.com/300?text=iPhone+15").build(),
                Product.builder().name("Samsung Galaxy S24").description("Samsung flagship")
                        .price(new BigDecimal("85000")).stock(40).category("Electronics")
                        .imageUrl("https://via.placeholder.com/300?text=Galaxy+S24").build(),
                Product.builder().name("Dell XPS 15 Laptop").description("Premium laptop")
                        .price(new BigDecimal("155000")).stock(25).category("Electronics")
                        .imageUrl("https://via.placeholder.com/300?text=Dell+XPS").build(),
                Product.builder().name("Nike Air Max").description("Running shoes")
                        .price(new BigDecimal("8500")).stock(100).category("Footwear")
                        .imageUrl("https://via.placeholder.com/300?text=Nike+AirMax").build(),
                Product.builder().name("Adidas Samba").description("Classic sneakers")
                        .price(new BigDecimal("7200")).stock(80).category("Footwear")
                        .imageUrl("https://via.placeholder.com/300?text=Adidas+Samba").build(),
                Product.builder().name("Levi's 501 Jeans").description("Original blue jeans")
                        .price(new BigDecimal("3500")).stock(150).category("Clothing")
                        .imageUrl("https://via.placeholder.com/300?text=Levis+501").build(),
                Product.builder().name("Cotton T-Shirt").description("Premium cotton tee")
                        .price(new BigDecimal("799")).stock(200).category("Clothing")
                        .imageUrl("https://via.placeholder.com/300?text=T-Shirt").build(),
                Product.builder().name("Sony WH-1000XM5").description("Noise cancelling headphones")
                        .price(new BigDecimal("28000")).stock(30).category("Electronics")
                        .imageUrl("https://via.placeholder.com/300?text=Sony+WH").build(),
                Product.builder().name("The Alchemist Book").description("Paulo Coelho novel")
                        .price(new BigDecimal("299")).stock(500).category("Books")
                        .imageUrl("https://via.placeholder.com/300?text=Alchemist").build(),
                Product.builder().name("Atomic Habits Book").description("James Clear")
                        .price(new BigDecimal("450")).stock(300).category("Books")
                        .imageUrl("https://via.placeholder.com/300?text=Atomic+Habits").build()
            );
            productRepo.saveAll(products);
            System.out.println(">>> " + products.size() + " sample products seeded");
        }
    }
}
