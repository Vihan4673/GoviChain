package lk.ijse.identity_access_service.controller;

import lk.ijse.identity_access_service.dto.UserResponse;
import lk.ijse.identity_access_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Internal service-to-service API, called via Feign Client from Marketplace,
 * Order & Payment, Logistics, etc. (see Architecture report section 5).
 * In production this should be locked down at the network/gateway level so it
 * is not reachable from the public internet.
 */
@RestController
@RequestMapping("/api/identity/internal")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public UserResponse getUserForService(@PathVariable UUID userId) {
        return userService.getForInternalUse(userId);
    }
}
