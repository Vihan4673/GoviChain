package lk.ijse.identity_access_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Farmer-specific data: farm size + map location (SRS FR-01, section 1.1.1)
 */
@Entity
@Table(name = "farmer_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FarmerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // 1-to-1 link to the core User record
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(nullable = false)
    private BigDecimal farmSizeInAcres;

    @Column(nullable = false)
    private Double farmLatitude;

    @Column(nullable = false)
    private Double farmLongitude;

    private String district;
    private String divisionalSecretariat;
    private String gramaNiladhariDivision;

    private String irrigationType; // rainfed / irrigated / both
}
