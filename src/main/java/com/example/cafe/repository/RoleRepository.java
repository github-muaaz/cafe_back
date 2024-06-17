package com.example.cafe.repository;

import com.example.cafe.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    boolean existsByName(String name);

    List<Role> findAllByIdIsNot(UUID id);

    Optional<Role> findByNameContainsIgnoreCase(String name);

    @Query(value = "SELECT * FROM role WHERE name = 'User'", nativeQuery = true)
    Role getDefaultRole();
}
