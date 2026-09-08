package lk.ijse.identity_access_service.repository;

import lk.ijse.identity_access_service.entity.FarmerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FarmerProfileRepository extends JpaRepository<FarmerProfile, UUID> {
    Optional<FarmerProfile> findByUserId(UUID userId);
}
