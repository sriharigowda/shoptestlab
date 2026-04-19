package com.shoptestlab.service;

import com.shoptestlab.dto.AuthDtos.*;
import com.shoptestlab.model.User;
import com.shoptestlab.repository.UserRepository;
import com.shoptestlab.security.JwtTokenProvider;
import com.shoptestlab.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername()))
            throw new BadRequestException("Username already taken");
        if (userRepository.existsByEmail(req.getEmail()))
            throw new BadRequestException("Email already registered");

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .fullName(req.getFullName())
                .role(User.Role.USER)
                .build();

        userRepository.save(user);
        return buildAuthResponse(user);
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        return buildAuthResponse(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), java.util.Collections.emptyList());
        String token = tokenProvider.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
