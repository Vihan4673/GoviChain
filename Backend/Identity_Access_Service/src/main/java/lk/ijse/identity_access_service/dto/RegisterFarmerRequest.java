package lk.ijse.identity_access_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Farmer registration payload — SRS FR-01
 */
@Data
public class RegisterFarmerRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "NIC number is required")
    @Pattern(regexp = "^([0-9]{9}[vVxX]|[0-9]{12})$", message = "Invalid Sri Lankan NIC format")
    private String nic;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Invalid contact number format")
    private String contactNumber;

    @NotBlank @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank
    private String address;

    private String bankAccountNumber;
    private String bankName;
    private String bankBranch;

    @NotNull(message = "Farm size is required")
    @DecimalMin(value = "0.01", message = "Farm size must be greater than 0")
    private BigDecimal farmSizeInAcres;

    @NotNull(message = "Farm location (latitude) is required")
    private Double farmLatitude;

    @NotNull(message = "Farm location (longitude) is required")
    private Double farmLongitude;

    private String district;
    private String divisionalSecretariat;
    private String gramaNiladhariDivision;
    private String irrigationType;
}
