package com.example.cafe.services.category;

import com.example.cafe.entity.Category;
import com.example.cafe.entity.Product;
import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.category.CategoryAddEditDTO;
import com.example.cafe.payload.category.CategoryDTO;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.repository.CategoryRepository;
import com.example.cafe.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public ApiResult<CategoryDTO> add(CategoryAddEditDTO categoryAddDTO) {
        if (categoryRepository.existsByName(categoryAddDTO.getName()))
            throw RestException
                    .restThrow(MessageConstants.CATEGORY_ALREADY_EXIST, HttpStatus.CONFLICT);

        Category parentCategory;

        if (Objects.isNull(categoryAddDTO.getParentId()))
            parentCategory = null;
        else
            parentCategory = categoryRepository
                .findById(categoryAddDTO.getParentId())
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.PARENT_NOT_FOUND, HttpStatus.NOT_FOUND));

        Category category = Category.builder()
                .name(categoryAddDTO.getName())
                .parent(parentCategory)
                .status(UniStatusEnum.ACTIVE)
                .build();

        category = categoryRepository.save(category);

        return ApiResult.successResponse(CategoryDTO.map(category), MessageConstants.SUCCESSFULLY_ADDED);
    }

    @Override
    public ApiResult<CategoryDTO> get(UUID id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(CategoryDTO.map(category));
    }

    @Override
    public ApiResult<List<CategoryDTO>> getAll(Integer page, Integer size) {
        Page<Category> pagedCategories = categoryRepository
                .findAllByDeletedOrderByUpdatedAtDesc(false, PageRequest.of(page, size));

        return ApiResult.successResponse(CategoryDTO
                .map(pagedCategories.getContent()));
    }

    @Override
    public ApiResult<List<CategoryDTO>> getAll(String parentName) {
        Category category = categoryRepository.findByNameIgnoreCase(parentName)
                .orElse(null);

        List<Category> categories = categoryRepository.findAllByParentAndStatus(category, UniStatusEnum.ACTIVE);

        return ApiResult.successResponse(CategoryDTO.map(categories));
    }

    @Override
    public ApiResult<CategoryDTO> edit(UUID id, CategoryAddEditDTO categoryDTO) {
        Category category = getCategory(id);

        category.setName(categoryDTO.getName());
        category.setParent(getCategory(categoryDTO.getParentId()));

        categoryRepository.save(category);

        return ApiResult.successResponse(CategoryDTO.map(category), MessageConstants.SUCCESSFULLY_EDITED);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        getCategory(id);

        categoryRepository.deleteById(id);

        return ApiResult.successResponse(MessageConstants.SUCCESSFULLY_DELETED);
    }

    @Override
    public ApiResult<CategoryDTO> editStatus(UUID id, UniStatusEnum status) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));

        category.setStatus(status);

        categoryRepository.save(category);

        return ApiResult.successResponse(CategoryDTO.map(category));
    }

    @Override
    public ApiResult<List<CategoryDTO>> search(Integer size, String search) {
        Page<Category> categories = categoryRepository.findAllByNameContainsIgnoreCase(search, PageRequest.of(0, size));

        return ApiResult.successResponse(CategoryDTO.map(categories.getContent()));
    }

    private Category getCategory(UUID id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
