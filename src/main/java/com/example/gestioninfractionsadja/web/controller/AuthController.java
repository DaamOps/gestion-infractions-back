package com.example.gestioninfractionsadja.web.controller;

import com.example.gestioninfractionsadja.data.dto.request.LoginRequest;
import com.example.gestioninfractionsadja.data.dto.request.RegisterRequest;
import com.example.gestioninfractionsadja.data.dto.response.JwtResponse;
import com.example.gestioninfractionsadja.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "authentification & registration", description = "Api authentification & registration")
public class AuthController {

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        JwtResponse jwtRes = authService.authenticate(loginRequest);

        if (jwtRes == null)
            throw new RuntimeException("Error: Compte désactivé ou supprimé.");

        return ResponseEntity.ok(jwtRes);
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> logoutUser() {

        authService.logout();

        return ResponseEntity.ok("OK");
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        JwtResponse jwtRes = authService.register(registerRequest);

        if (jwtRes == null)
            throw new RuntimeException("Error: Inscription non validé.");

        return ResponseEntity.ok(jwtRes);
    }
}
