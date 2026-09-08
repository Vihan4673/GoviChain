package lk.ijse.identity_access_service.repository;

import lk.ijse.identity_access_service.entity.User;
import lk.ijse.identity_access_service.entity.UserRole; // නිවැරදි UserRole enum/entity එක import කරන ලදී
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNicOrBrNumber(String nicOrBrNumber);

    @Query("SELECT u FROM User u WHERE " +
            "(:name IS NULL OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:nic IS NULL OR u.nicOrBrNumber = :nic) AND " +
            "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:role IS NULL OR u.role = :role)")
    List<User> search(
            @Param("name") String name,
            @Param("nic") String nic,
            @Param("email") String email,
            @Param("role") UserRole role
    );
}