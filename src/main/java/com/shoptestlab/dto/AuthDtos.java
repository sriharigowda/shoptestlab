package com.shoptestlab.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public class AuthDtos {

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class RegisterRequest {
        @NotBlank @Size(min = 3, max = 50)
        private String username;

        @NotBlank @Email
        private String email;

        @NotBlank @Size(min = 6)
        private String password;

        private String fullName;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank
        private String username;

        @NotBlank
        private String password;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AuthResponse {
        private String token;
        private String username;
        private String email;
        private String role;
    }
}
