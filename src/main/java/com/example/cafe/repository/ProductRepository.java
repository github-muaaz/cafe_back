package com.example.cafe.repository;

import com.example.cafe.entity.Category;
import com.example.cafe.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAllByDeletedOrderByUpdatedAtDesc(boolean deleted, Pageable pageable);

    boolean existsByTitle(String title);

    Page<Product> findByCategoriesIn(List<Category> categories, Pageable pageable);

    List<Product> findAllByTitleContainingIgnoreCase(String search, Pageable pageable);
}