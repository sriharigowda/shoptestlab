package com.shoptestlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopTestLabApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopTestLabApplication.class, args);
        System.out.println("==============================================");
        System.out.println("ShopTestLab is running!");
        System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
        System.out.println("==============================================");
    }
}
