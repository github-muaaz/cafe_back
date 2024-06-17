package com.example.cafe.repository;

import com.example.cafe.entity.User;
import com.example.cafe.entity.enums.PermissionEnum;
import lombok.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findAllByRole_PermissionsContains(PermissionEnum role_permission);

    boolean existsByRoleId(UUID id);

    boolean existsById(@NonNull UUID id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET role = :insteadOfRoleId WHERE role = :id")
    void updateRole(UUID id, UUID insteadOfRoleId);

    List<User> findAllByDeletedFalse(Sort sort);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String username);
}
