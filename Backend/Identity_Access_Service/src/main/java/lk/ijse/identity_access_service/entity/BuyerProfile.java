package lk.ijse.identity_access_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Buyer-specific data (SRS section 1.1.2)
 */
@Entity
@Table(name = "buyer_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    private String organizationName;
    private String buyerType; // wholesaler / retailer / exporter / processor / supermarket / hotel
    private String preferredCommodityTypes;
    private String purchaseVolumeCapacity;
}
