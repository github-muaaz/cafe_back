package com.example.cafe.services.language;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.entity.language.Language;
import com.example.cafe.entity.language.LanguageKey;
import com.example.cafe.entity.language.LanguageValue;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.language.*;
import com.example.cafe.repository.LanguageKeyRepository;
import com.example.cafe.repository.LanguageRepository;
import com.example.cafe.repository.LanguageValueRepository;
import com.example.cafe.utils.MessageConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageKeyRepository languageKeyRepository;
    private final LanguageValueRepository languageValueRepository;
    
    @Override
    public ApiResult<?> addOrEditLanguage(LanguageDTO languageDTO) {

        Language language;

        if (Objects.isNull(languageDTO.getId())){
            if (languageRepository.existsByNameOrCode(languageDTO.getName(), languageDTO.getCode()))
                throw RestException
                        .restThrow(MessageConstants.ALREADY_EXISTS, HttpStatus.CONFLICT);

            language = new Language();
            language.setStatus(UniStatusEnum.NON_ACTIVE);
        } else {
            if (languageRepository.existsByNameAndIdNotOrCodeAndIdNot(
                    languageDTO.getName(), languageDTO.getId(),
                    languageDTO.getCode(), languageDTO.getId())
            )
                throw RestException
                        .restThrow(MessageConstants.ALREADY_EXISTS, HttpStatus.CONFLICT);

            language = getLanguageByIdOrElseThrow(languageDTO.getId());
            language.setStatus(languageDTO.getStatus());
        }

        language.setName(languageDTO.getName().trim());
        language.setCode(languageDTO.getCode().trim());

        language = languageRepository.save(language);
        
        return ApiResult.successResponse(mapToLanguageDTO(language), "Successfully updated");
    }

    @Override
    public ApiResult<?> getLanguage(UUID id) {
        Language language = getLanguageByIdOrElseThrow(id);
        
        return ApiResult.successResponse(mapToLanguageDTO(language));
    }

    @Override
    public ApiResult<?> getLanguages(boolean isActive) {
        
        List<Language> languages = languageRepository.findAll();
        
        return ApiResult.successResponse(mapToLanguageDTO(languages));
    }

    @Override
    public ApiResult<?> deleteLanguage(UUID id) {
        checkLanguageExistsById(id);
        
        languageRepository.deleteById(id);
        
        return ApiResult.successResponse("Successfully deleted");
    }




    @Override
    public ApiResult<?> addOrEditLanguageKey(List<LanguageKeyDTO> languageKeys) {

        LanguageKey languageKey = null;
        Map<String, String> map = new HashMap<>();

        for (LanguageKeyDTO languageKeyDTO : languageKeys) {
            if (Objects.isNull(languageKeyDTO.getId())){
                if(languageKeyRepository.existsByKey(languageKeyDTO.getKey()))
                    throw RestException
                            .restThrow(MessageConstants.ALREADY_EXISTS, HttpStatus.CONFLICT);

                languageKey = new LanguageKey();

                languageKey.setKey(languageKeyDTO.getKey().trim());

                languageKey = languageKeyRepository.save(languageKey);

                map.put(languageKey.getKey(), null);
            } else {
                if (languageKeyRepository.existsByKeyAndIdNot(languageKeyDTO.getKey(), languageKeyDTO.getId()))
                    throw RestException
                            .restThrow(MessageConstants.ALREADY_EXISTS, HttpStatus.CONFLICT);

                languageKey = getLanguageKeyByIdOrElseThrow(languageKeyDTO.getId());

                languageKey.setKey(languageKeyDTO.getKey().trim());
                languageKey.setDeleted(languageKeyDTO.isDeleted());

                languageKey = languageKeyRepository.save(languageKey);

                map.put(languageKey.getKey(), null);
            }
        }

        return ApiResult.successResponse(map);
    }

    @Override
    public ApiResult<?> deleteLanguageKey(UUID id) {
        getLanguageKeyByIdOrElseThrow(id);

        languageKeyRepository.deleteById(id);

        return ApiResult.successResponse("Successfully deleted");
    }




    @Override
    public ApiResult<?> addOrEditLanguageValue(UUID languageKeyId, List<LanguageValuesAddEditDTO> languageValues) {
        Language language;
        LanguageKey languageKey;
        LanguageValue languageValue;

        List<LanguageValue> newLanguageValues = new ArrayList<>();

        for (LanguageValuesAddEditDTO valuesDTO : languageValues) {
//            if languageValue is new, else languageValue is updating
            if (Objects.isNull(valuesDTO.getId())){
                language = getLanguageByIdOrElseThrow(valuesDTO.getLanguageId());

                languageKey = getLanguageKeyByIdOrElseThrow(valuesDTO.getLanguageKeyId());

                newLanguageValues
                        .add(LanguageValue.builder()
                                .value(valuesDTO.getValue().trim())
                                .language(language)
                                .languageKey(languageKey)
                                .build());
            } else {
                getLanguageByIdOrElseThrow(valuesDTO.getLanguageId());

                getLanguageKeyByIdOrElseThrow(valuesDTO.getLanguageKeyId());

                languageValue = languageValueRepository
                        .findById(valuesDTO.getId())
                        .orElseThrow(() ->
                                RestException
                                        .restThrow(MessageConstants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND));

                languageValue.setValue(valuesDTO.getValue().trim());

                newLanguageValues.add(languageValue);
            }
        }

        newLanguageValues = languageValueRepository.saveAll(newLanguageValues);

        return ApiResult.successResponse(getLanguageValueByKeyId(languageKeyId), "Successfully updated");
    }

    public LanguageValueDTO getLanguageValueByKeyId(UUID languageKeyId) {
        List<LanguageValueProjection> results = languageValueRepository.findByLanguageKeyId(languageKeyId);

        if (results.isEmpty()) {
            return null;
        }

        LanguageValueDTO aggregatedResult = new LanguageValueDTO();
        aggregatedResult.setId(results.get(0).getKeyId());
        aggregatedResult.setKey(results.get(0).getKeyName());

        List<LanguageValueDTO.ValueDTO> values = results.stream()
                .map(result -> new LanguageValueDTO.ValueDTO(
                        result.getValueId(),
                        result.getValueText(),
                        result.getLanguageId(),
                        result.getLanguageName()
                ))
                .collect(Collectors.toList());

        aggregatedResult.setValues(values);

        return aggregatedResult;
    }

    private LanguageValueDTO mapToLangValues(List<LanguageValuesAddEditDTO> languageValues, List<LanguageValue> newLanguageValues) {
        LanguageValueDTO langValue = new LanguageValueDTO();

        LanguageValuesAddEditDTO first = languageValues.stream().findFirst().orElseThrow();

        langValue.setId(first.getLanguageKeyId());
        langValue.setKey(null);
        langValue.setValues(newLanguageValues
                .stream()
                .map(languageValue -> {
                    LanguageValueDTO.ValueDTO valueDTO = new LanguageValueDTO.ValueDTO();

                    valueDTO.setId(languageValue.getId());
                    valueDTO.setLanguageId(languageValue.getLanguage().getId());
                    valueDTO.setLanguageName(languageValue.getLanguage().getName());
                    valueDTO.setValue(languageValue.getValue());

                    return valueDTO;
                })
                .collect(Collectors.toList()));

        return langValue;
    }

    @Override
    public Map<Object, Object> getValuesByLang(String languageCode) {

        String valuesByLang = languageValueRepository.getValuesByLang(languageCode);

        try {
            if (valuesByLang != null) {
                return new ObjectMapper().readValue(valuesByLang, new TypeReference<>(){});
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw RestException.restThrow("Parse qilishda xatolik", HttpStatus.BAD_REQUEST);
        }
        return new HashMap<>();
    }

    @Override
    public ApiResult<LanguageDTO> editStatus(UUID id, UniStatusEnum status) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.LANGUAGE_NOT_FOUND, HttpStatus.NOT_FOUND));

        language.setStatus(status);

        languageRepository.save(language);

        return ApiResult.successResponse(mapToLanguageDTO(language));
    }

    @Override
    public ApiResult<List<LanguageValueDTO>> getKeyValues(Integer page, Integer size) {

        return ApiResult.successResponse(getLanguageValues());
    }

    public List<LanguageValueDTO> getLanguageValues() {
        List<LanguageValueProjection> projections = languageValueRepository.findLanguageValues();

        Map<UUID, LanguageValueDTO> languageValueMap = new HashMap<>();

        for (LanguageValueProjection projection : projections) {
            UUID keyId = projection.getKeyId();
            LanguageValueDTO languageValueDTO = languageValueMap.getOrDefault(keyId, new LanguageValueDTO());
            languageValueDTO.setId(keyId);
            languageValueDTO.setKey(projection.getKeyName());

            LanguageValueDTO.ValueDTO valueDTO = new LanguageValueDTO.ValueDTO();
            valueDTO.setId(projection.getValueId());
            valueDTO.setValue(projection.getValueText());
            valueDTO.setLanguageId(projection.getLanguageId());
            valueDTO.setLanguageName(projection.getLanguageName());

            languageValueDTO.getValues().add(valueDTO);
            languageValueMap.put(keyId, languageValueDTO);
        }

        return new ArrayList<>(languageValueMap.values());
    }


    private LanguageKey getLanguageKeyByIdOrElseThrow(UUID id) {
        return languageKeyRepository.findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private void checkLanguageExistsById(UUID id) {
        if (!languageRepository.existsById(id))
            throw RestException
                    .restThrow(MessageConstants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    private Language getLanguageByIdOrElseThrow(UUID id) {
        return languageRepository
                .findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private LanguageDTO mapToLanguageDTO(Language language) {
        return LanguageDTO.builder()
                .id(language.getId())
                .name(language.getName())
                .code(language.getCode())
                .status(language.getStatus())
                .build();
    }

    private List<LanguageDTO> mapToLanguageDTO(List<Language> languages) {
        return languages
                .stream()
                .map(this::mapToLanguageDTO)
                .collect(Collectors.toList());
    }

}