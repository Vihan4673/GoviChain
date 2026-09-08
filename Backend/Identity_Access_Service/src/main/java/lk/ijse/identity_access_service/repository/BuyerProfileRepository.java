package lk.ijse.identity_access_service.repository;

import lk.ijse.identity_access_service.entity.BuyerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BuyerProfileRepository extends JpaRepository<BuyerProfile, UUID> {
    Optional<BuyerProfile> findByUserId(UUID userId);
}
