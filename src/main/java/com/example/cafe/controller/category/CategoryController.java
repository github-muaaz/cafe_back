package com.example.cafe.controller.category;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.category.CategoryAddEditDTO;
import com.example.cafe.payload.category.CategoryDTO;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.utils.RestConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = CategoryController.CATEGORY_CONTROLLER_BASE_PATH)
public interface CategoryController {
    String CATEGORY_CONTROLLER_BASE_PATH = RestConstants.BASE_PATH + "category";
    String CATEGORY_ID_PATH = "/{id}";
    String SEARCH_PATH = "/query/{size}";
    String EDIT_STATUS_PATH = "/status/{id}/{status}";
    String GET_CATEGORY_LIST_PATH = "/list/{page}/{size}";
    String GET_ALL_CATEGORIES_BY_PARENT_PATH = "/parent/{parentName}";

    @ApiOperation(value = "add new category")
    @PostMapping
    ApiResult<CategoryDTO> add(@RequestBody CategoryAddEditDTO category);

    @ApiOperation(value = "get one category")
    @GetMapping(value = CATEGORY_ID_PATH)
    ApiResult<CategoryDTO> get(@PathVariable UUID id);

    @ApiOperation(value = "get list of category")
    @GetMapping(value = GET_CATEGORY_LIST_PATH)
    ApiResult<List<CategoryDTO>> getAll(@PathVariable Integer page, @PathVariable Integer size);

    @ApiOperation(value = "get list of category (for waiter)")
    @GetMapping(value = GET_ALL_CATEGORIES_BY_PARENT_PATH)
    ApiResult<List<CategoryDTO>> getAll(@PathVariable(name = "parentName") String parentName);

    @ApiOperation(value = "edit category")
    @PutMapping(value = CATEGORY_ID_PATH)
    ApiResult<CategoryDTO> edit(@PathVariable UUID id, @RequestBody CategoryAddEditDTO category);

    @ApiOperation(value = "delete category")
    @DeleteMapping(value = CATEGORY_ID_PATH)
    ApiResult<?> delete(@PathVariable UUID id);

    @ApiOperation(value = "edit status of product")
    @PutMapping(value = EDIT_STATUS_PATH)
    ApiResult<CategoryDTO> editStatus(@PathVariable UUID id, @PathVariable UniStatusEnum status);

    @ApiOperation(value = "search skill")
    @GetMapping(path = SEARCH_PATH)
    ApiResult<List<CategoryDTO>> search(@PathVariable Integer size, @RequestParam String search);
}
