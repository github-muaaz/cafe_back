package com.example.cafe.payload.language;

import java.util.UUID;

public interface LanguageValueProjection {
    UUID getKeyId();
    String getKeyName();
    UUID getValueId();
    String getValueText();
    UUID getLanguageId();
    String getLanguageName();
}