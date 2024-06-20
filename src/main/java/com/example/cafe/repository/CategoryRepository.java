package com.example.cafe.repository;

import com.example.cafe.entity.Category;
import com.example.cafe.entity.enums.UniStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByName(String name);

    Page<Category> findAllByDeletedOrderByUpdatedAtDesc(boolean deleted, Pageable pageable);

    List<Category> findAllByParentIsNull();

    List<Category> findAllByParentName(String parentName);

    Optional<Category> findByName(String parentName);

    List<Category> findAllByParentId(UUID id);

    List<Category> findAllByParentAndStatus(Category category, UniStatusEnum status);

    List<Category> findAllByNameContainingIgnoreCase(String search, Pageable pageable);

    Page<Category> findAllByNameContainsIgnoreCase(String search, Pageable pageable);

    Optional<Category> findByNameIgnoreCase(String mainCategoryName);
}
