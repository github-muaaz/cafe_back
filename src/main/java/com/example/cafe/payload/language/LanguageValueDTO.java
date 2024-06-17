package com.example.cafe.payload.language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LanguageValueDTO {
    private UUID id;
    private String key;
    private List<ValueDTO> values = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValueDTO {
        private UUID id;
        private String value;
        private UUID languageId;
        private String languageName;
    }

    public LanguageValueDTO(UUID id, String key, ValueDTO valueDTO) {
        this.id = id;
        this.key = key;
        this.values.add(valueDTO);
    }
}