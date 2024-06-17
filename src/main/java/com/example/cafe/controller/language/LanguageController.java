package com.example.cafe.controller.language;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.language.LanguageDTO;
import com.example.cafe.payload.language.LanguageKeyDTO;
import com.example.cafe.payload.language.LanguageValueDTO;
import com.example.cafe.payload.language.LanguageValuesAddEditDTO;
import com.example.cafe.utils.RestConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping(path = LanguageController.LANGUAGE_CONTROLLER_BASE_PATH)
public interface LanguageController {

    String LANGUAGE_CONTROLLER_BASE_PATH = RestConstants.BASE_PATH + "language";
    String GET_LANGUAGES_PATH = "";
    String ID_PATH = "/{id}";
    String EDIT_STATUS_PATH = "/status/{id}/{status}";
    String LANGUAGE_KEY_PATH = "/key";
    String LANGUAGE_KEY_DELETE_PATH = LANGUAGE_KEY_PATH + ID_PATH;
    String LANGUAGE_VALUE_PATH = "/value/{keyId}";
    String LANGUAGE_KEY_VALUE_PATH = "/key-value/{page}/{size}";
    String GET_VALUES_BY_LANGUAGE_PATH = "/value/{language}";


//    Language
    @PostMapping
    ApiResult<?> addOrEditLanguage(@RequestBody LanguageDTO languageDTO);

    @GetMapping(path = ID_PATH)
    ApiResult<?> getLanguage(@PathVariable UUID id);

    @GetMapping(path = GET_LANGUAGES_PATH)
    ApiResult<?> getLanguages();

    @DeleteMapping(path = ID_PATH)
    ApiResult<?> deleteLanguage(@PathVariable UUID id);

    @ApiOperation(value = "edit status of product")
    @PutMapping(value = EDIT_STATUS_PATH)
    ApiResult<LanguageDTO> editStatus(@PathVariable UUID id, @PathVariable UniStatusEnum status);



    //    LanguageKey
    @PostMapping(path = LANGUAGE_KEY_PATH)
    ApiResult<?> addOrEditLanguageKey(@RequestBody List<LanguageKeyDTO> languageKeys);

    @DeleteMapping(path = LANGUAGE_KEY_DELETE_PATH)
    ApiResult<?> deleteLanguageKey(@PathVariable UUID id);


//    LanguageValue
    @PostMapping(path = LANGUAGE_VALUE_PATH)
    ApiResult<?> addOrEditLanguageValue(@PathVariable("keyId") UUID languageKeyId, @RequestBody List<LanguageValuesAddEditDTO> languageValues);

    @GetMapping(path = GET_VALUES_BY_LANGUAGE_PATH)
    Map<Object, Object> getValuesByLang(@PathVariable String language);

    @GetMapping(path = LANGUAGE_KEY_VALUE_PATH)
    ApiResult<List<LanguageValueDTO>> getKeyValues(@PathVariable Integer page, @PathVariable Integer size);
}
