package com.example.cafe.repository;

import com.example.cafe.entity.Tables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TableRepository extends JpaRepository<Tables, UUID> {
    boolean existsByName(String name);

    Page<Tables> findAllByDeletedOrderByUpdatedAtDesc(boolean deleted, Pageable pageable);
}
