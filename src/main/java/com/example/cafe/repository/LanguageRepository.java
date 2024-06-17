package com.example.cafe.repository;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.entity.language.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LanguageRepository extends JpaRepository<Language, UUID> {
    Optional<Language> findByCode(String code);

    boolean existsByNameAndIdNotOrCodeAndIdNot(String name, UUID id, String code, UUID id2);

    boolean existsByNameOrCode(String name, String code);

    List<Language> findAllByStatus(UniStatusEnum uniStatusEnum);
}
