package com.example.cafe.services.language;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.language.LanguageDTO;
import com.example.cafe.payload.language.LanguageKeyDTO;
import com.example.cafe.payload.language.LanguageValueDTO;
import com.example.cafe.payload.language.LanguageValuesAddEditDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface LanguageService {

    ApiResult<?> addOrEditLanguage(LanguageDTO languageDTO);

    ApiResult<?> getLanguage(UUID id);

    ApiResult<?> getLanguages(boolean isActive);

    ApiResult<?> deleteLanguage(UUID id);



    ApiResult<?> addOrEditLanguageKey(List<LanguageKeyDTO> languageKeys);

    ApiResult<?> deleteLanguageKey(UUID id);



    ApiResult<?> addOrEditLanguageValue(UUID languageKeyId, List<LanguageValuesAddEditDTO> languageValues);

    Map<Object, Object> getValuesByLang(String language);

    ApiResult<LanguageDTO> editStatus(UUID id, UniStatusEnum status);

    ApiResult<List<LanguageValueDTO>> getKeyValues(Integer page, Integer size);
}