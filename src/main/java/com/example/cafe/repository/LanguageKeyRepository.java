package com.example.cafe.repository;

import com.example.cafe.entity.language.LanguageKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LanguageKeyRepository extends JpaRepository<LanguageKey, UUID> {

    boolean existsByKeyAndIdNot(String value, UUID id);

    boolean existsByKey(String value);
}
