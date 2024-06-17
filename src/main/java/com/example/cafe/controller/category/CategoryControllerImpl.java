package com.example.cafe.controller.category;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.category.CategoryAddEditDTO;
import com.example.cafe.payload.category.CategoryDTO;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    public ApiResult<CategoryDTO> add(CategoryAddEditDTO category) {
        return categoryService.add(category);
    }

    @Override
    public ApiResult<CategoryDTO> get(UUID id) {
        return categoryService.get(id);
    }

    @Override
    public ApiResult<List<CategoryDTO>> getAll(Integer page, Integer size) {
        return categoryService.getAll(page, size);
    }

    @Override
    public ApiResult<List<CategoryDTO>> getAll(String parentName) {
        return categoryService.getAll(parentName);
    }

    @Override
    public ApiResult<CategoryDTO> edit(UUID id, CategoryAddEditDTO category) {
        return categoryService.edit(id, category);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return categoryService.delete(id);
    }

    @Override
    public ApiResult<CategoryDTO> editStatus(UUID id, UniStatusEnum status) {
        return categoryService.editStatus(id, status);
    }

    @Override
    public ApiResult<List<CategoryDTO>> search(Integer size, String search) {
        return categoryService.search(size, search);
    }
}
