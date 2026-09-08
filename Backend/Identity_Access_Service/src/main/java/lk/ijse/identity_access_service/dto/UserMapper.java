package lk.ijse.identity_access_service.dto;


import lk.ijse.identity_access_service.entity.User;

public class UserMapper {

    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .contactNumber(user.getContactNumber())
                .role(user.getRole())
                .status(user.getStatus())
                .verified(user.isVerified())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
