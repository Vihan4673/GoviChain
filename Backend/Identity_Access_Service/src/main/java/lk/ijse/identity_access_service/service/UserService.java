package lk.ijse.identity_access_service.service;

import lk.ijse.identity_access_service.dto.UserMapper;
import lk.ijse.identity_access_service.dto.UserResponse;
import lk.ijse.identity_access_service.entity.AccountStatus;
import lk.ijse.identity_access_service.entity.User; // Spring Security User වෙනුවට ඔබගේ Entity Class එක Import කරන ලදී
import lk.ijse.identity_access_service.entity.UserRole;
import lk.ijse.identity_access_service.exception.UserNotFoundException;
import lk.ijse.identity_access_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        return UserMapper.toResponse(user);
    }

    public List<UserResponse> search(String name, String nic, String email, UserRole role) {
        return userRepository.search(name, nic, email, role)
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    /**
     * Admin action: suspend account with a mandatory reason (SRS Admin User Story #1).
     */
    @Transactional
    public UserResponse suspend(UUID userId, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        user.setStatus(AccountStatus.SUSPENDED);
        userRepository.save(user);
        // TODO: call Communication & Support Service to notify the user with `reason`
        return UserMapper.toResponse(user);
    }

    @Transactional
    public UserResponse activate(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
        user.setStatus(AccountStatus.ACTIVE);
        user.setVerified(true);
        userRepository.save(user);
        return UserMapper.toResponse(user);
    }

    /**
     * Used internally by other services (via Feign) to validate a user reference
     * without exposing sensitive fields.
     */
    public UserResponse getForInternalUse(UUID userId) {
        return getById(userId);
    }
}