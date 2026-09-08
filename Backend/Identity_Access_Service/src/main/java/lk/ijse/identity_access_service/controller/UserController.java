package lk.ijse.identity_access_service.controller;


import lk.ijse.identity_access_service.dto.UserResponse;
import lk.ijse.identity_access_service.entity.UserRole;
import lk.ijse.identity_access_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Admin-only endpoints. Access restricted via SecurityConfig to ROLE_SYSTEM_ADMIN.
 * Corresponds to SRS Admin User Story #1 (User Account Management).
 */
@RestController
@RequestMapping("/api/identity/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable UUID userId) {
        return userService.getById(userId);
    }

    @GetMapping("/search")
    public List<UserResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nic,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) UserRole role) {
        return userService.search(name, nic, email, role);
    }

    @PatchMapping("/{userId}/suspend")
    public UserResponse suspend(@PathVariable UUID userId, @RequestBody Map<String, String> body) {
        String reason = body.getOrDefault("reason", "Not specified");
        return userService.suspend(userId, reason);
    }

    @PatchMapping("/{userId}/activate")
    public UserResponse activate(@PathVariable UUID userId) {
        return userService.activate(userId);
    }
}
