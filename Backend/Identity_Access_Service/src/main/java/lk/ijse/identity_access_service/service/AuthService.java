package lk.ijse.identity_access_service.service;

import lk.ijse.identity_access_service.dto.AuthResponse;
import lk.ijse.identity_access_service.dto.LoginRequest;
import lk.ijse.identity_access_service.dto.RegisterFarmerRequest;
import lk.ijse.identity_access_service.entity.AccountStatus;
import lk.ijse.identity_access_service.entity.FarmerProfile;
import lk.ijse.identity_access_service.entity.User;
import lk.ijse.identity_access_service.entity.UserRole;
import lk.ijse.identity_access_service.exception.DuplicateUserException;
import lk.ijse.identity_access_service.exception.InvalidCredentialsException;
import lk.ijse.identity_access_service.repository.FarmerProfileRepository;
import lk.ijse.identity_access_service.repository.UserRepository;
import lk.ijse.identity_access_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final FarmerProfileRepository farmerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * SRS FR-01: Farmer registration with farm size + map location.
     * System prevents duplicate registration using same NIC/email.
     */
    @Transactional
    public AuthResponse registerFarmer(RegisterFarmerRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException("An account with this email already exists");
        }
        if (userRepository.existsByNicOrBrNumber(request.getNic())) {
            throw new DuplicateUserException("An account with this NIC already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .nicOrBrNumber(request.getNic())
                .contactNumber(request.getContactNumber())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.FARMER)
                .address(request.getAddress())
                .bankAccountNumber(request.getBankAccountNumber())
                .bankName(request.getBankName())
                .bankBranch(request.getBankBranch())
                .status(AccountStatus.PENDING) // awaits admin verification, per SRS admin journey
                .verified(false)
                .build();

        user = userRepository.save(user);

        FarmerProfile profile = FarmerProfile.builder()
                .userId(user.getId())
                .farmSizeInAcres(request.getFarmSizeInAcres())
                .farmLatitude(request.getFarmLatitude())
                .farmLongitude(request.getFarmLongitude())
                .district(request.getDistrict())
                .divisionalSecretariat(request.getDivisionalSecretariat())
                .gramaNiladhariDivision(request.getGramaNiladhariDivision())
                .irrigationType(request.getIrrigationType())
                .build();

        farmerProfileRepository.save(profile);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .expiresInSeconds(jwtService.getExpirationSeconds())
                .build();
    }

    /**
     * SRS FR-02: Role-based login using email/username + password.
     */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if (user.getStatus() == AccountStatus.SUSPENDED) {
            throw new InvalidCredentialsException("This account has been suspended. Contact support.");
        }
        if (user.getStatus() == AccountStatus.DEACTIVATED) {
            throw new InvalidCredentialsException("This account has been deactivated.");
        }

        user.setLastLoginAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .expiresInSeconds(jwtService.getExpirationSeconds())
                .build();
    }
}
