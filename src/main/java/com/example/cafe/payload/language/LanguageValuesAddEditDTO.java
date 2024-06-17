package com.example.cafe.payload.language;

import lombok.*;

import java.util.UUID;

@Getter
public class LanguageValuesAddEditDTO {
    private UUID id;

    private UUID languageId;

    private UUID languageKeyId;

    private String value;
}
