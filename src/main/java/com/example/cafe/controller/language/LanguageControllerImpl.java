package com.example.cafe.controller.language;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.language.LanguageDTO;
import com.example.cafe.payload.language.LanguageKeyDTO;
import com.example.cafe.payload.language.LanguageValueDTO;
import com.example.cafe.payload.language.LanguageValuesAddEditDTO;
import com.example.cafe.services.language.LanguageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LanguageControllerImpl implements LanguageController{

    private final LanguageService languageService;

    @Override
    public ApiResult<?> addOrEditLanguage(LanguageDTO languageDTO) {
        return languageService.addOrEditLanguage(languageDTO);
    }
    @Override
    public ApiResult<?> getLanguage(UUID id) {
        return languageService.getLanguage(id);
    }
    @Override
    public ApiResult<?> getLanguages() {
        return languageService.getLanguages(true);
    }
    @Override
    public ApiResult<?> deleteLanguage(UUID id) {
        return languageService.deleteLanguage(id);
    }

    @Override
    public ApiResult<LanguageDTO> editStatus(UUID id, UniStatusEnum status) {
        return languageService.editStatus(id, status);
    }


    @Override
    public ApiResult<?> addOrEditLanguageKey(List<LanguageKeyDTO> languageKeys) {
        return languageService.addOrEditLanguageKey(languageKeys);
    }
    @Override
    public ApiResult<?> deleteLanguageKey(UUID id) {
        return languageService.deleteLanguageKey(id);
    }



    @Override
    public ApiResult<?> addOrEditLanguageValue(UUID languageKeyId, List<LanguageValuesAddEditDTO> languageValues) {
        return languageService.addOrEditLanguageValue(languageKeyId, languageValues);
    }

    @Override
    public Map<Object, Object> getValuesByLang(String language) {
        return languageService.getValuesByLang(language);
    }

    @Override
    public ApiResult<List<LanguageValueDTO>> getKeyValues(Integer page, Integer size) {
        return languageService.getKeyValues(page, size);
    }
}
