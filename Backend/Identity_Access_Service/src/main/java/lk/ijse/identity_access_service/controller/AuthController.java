package lk.ijse.identity_access_service.controller;

import jakarta.validation.Valid;

import lk.ijse.identity_access_service.dto.AuthResponse;
import lk.ijse.identity_access_service.dto.LoginRequest;
import lk.ijse.identity_access_service.dto.RegisterFarmerRequest;
import lk.ijse.identity_access_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/identity/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/farmer")
    public ResponseEntity<AuthResponse> registerFarmer(@Valid @RequestBody RegisterFarmerRequest request) {
        AuthResponse response = authService.registerFarmer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
