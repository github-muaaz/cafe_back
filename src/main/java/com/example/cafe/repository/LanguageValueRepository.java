package com.example.cafe.repository;

import com.example.cafe.entity.language.LanguageValue;
import com.example.cafe.payload.language.LanguageValueDTO;
import com.example.cafe.payload.language.LanguageValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LanguageValueRepository extends JpaRepository<LanguageValue, UUID> {

    @Query(value = "select cast(jsonb_object_agg(lk.key, lv.value) as varchar)\n" +
            "from language_key lk\n" +
            "         left join language_value lv\n" +
            "                   on lk.id = lv.language_key_id and\n" +
            "                      lv.language_id = (select id from language where deleted = false and code = :language)", nativeQuery = true)
    String getValuesByLang(@Param("language") String language);

    @Query(value = "SELECT CAST(lk.id AS VARCHAR) as keyId, lk.key as keyName, " +
            "CAST(lv.id AS VARCHAR) as valueId, lv.value as valueText, " +
            "CAST(l.id AS VARCHAR) as languageId, l.name as languageName " +
            "FROM language_key lk " +
            "LEFT JOIN language_value lv ON lk.id = lv.language_key_id AND lv.deleted = false " +
            "LEFT JOIN language l ON lv.language_id = l.id AND l.deleted = false " +
            "WHERE lk.deleted = false", nativeQuery = true)
    List<LanguageValueProjection> findLanguageValues();

    @Query(value = "SELECT CAST(lk.id AS VARCHAR) as keyId, lk.key as keyName, " +
            "CAST(lv.id AS VARCHAR) as valueId, lv.value as valueText, " +
            "CAST(l.id AS VARCHAR) as languageId, l.name as languageName " +
            "FROM language_key lk " +
            "LEFT JOIN language_value lv ON lk.id = lv.language_key_id AND lv.deleted = false " +
            "LEFT JOIN language l ON lv.language_id = l.id AND l.deleted = false " +
            "WHERE lk.id = :languageKeyId AND lk.deleted = false", nativeQuery = true)
    List<LanguageValueProjection> findByLanguageKeyId(@Param("languageKeyId") UUID languageKeyId);
}
