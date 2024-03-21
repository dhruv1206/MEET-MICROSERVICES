package com.ecommerce.users.auth;

import com.ecommerce.users.config.JwtService;
import com.ecommerce.users.user.Role;
import com.ecommerce.users.user.User;
import com.ecommerce.users.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest req) {
        final User user = User.builder()
                .email(req.getEmail())
                .phone(req.getPhone())
                .password(passwordEncoder.encode(req.getPassword()))
                .firstname(req.getFirstname())
                .lastname(req.getLastname())
                .username(req.getUsername())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        final String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(), req.getPassword()
                )
        );
        final User user = userRepository.findUserByUsername(req.getUsername())
                .orElseThrow();
        final String token = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(token).build();
    }
}
