package com.example.cafe.services.category;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.category.CategoryAddEditDTO;
import com.example.cafe.payload.category.CategoryDTO;
import com.example.cafe.payload.product.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    ApiResult<CategoryDTO> add(CategoryAddEditDTO category);

    ApiResult<CategoryDTO> get(UUID id);

    ApiResult<List<CategoryDTO>> getAll(Integer page, Integer size);

    ApiResult<List<CategoryDTO>> getAll(String parentName);

    ApiResult<CategoryDTO> edit(UUID id, CategoryAddEditDTO category);

    ApiResult<?> delete(UUID id);

    ApiResult<CategoryDTO> editStatus(UUID id, UniStatusEnum status);

    ApiResult<List<CategoryDTO>> search(Integer size, String search);

}
