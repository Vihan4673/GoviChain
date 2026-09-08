package lk.ijse.identity_access_service.dto;

import lk.ijse.identity_access_service.entity.AccountStatus;
import lk.ijse.identity_access_service.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Safe, external-facing representation of a User (no password, no full NIC exposed to other services).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String fullName;
    private String email;
    private String contactNumber;
    private UserRole role;
    private AccountStatus status;
    private boolean verified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}
