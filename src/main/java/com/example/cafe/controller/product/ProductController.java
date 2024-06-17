package com.example.cafe.controller.product;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.payload.product.SearchDTO;
import com.example.cafe.utils.RestConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequestMapping(path = ProductController.PRODUCT_CONTROLLER_BASE_PATH)
public interface ProductController {
    String PRODUCT_CONTROLLER_BASE_PATH = RestConstants.BASE_PATH + "product";
    String PRODUCT_ID_PATH = "/{id}";
    String PRODUCT_SEARCH_ID_PATH = "/search/{search}";
    String GET_PRODUCT_LIST_PATH = "/list/{page}/{size}";
    String EDIT_STATUS_PATH = "/status/{id}/{status}";
    String GET_PRODUCT_BY_CATEGORY_PATH = "/{mainCategoryName}/{categoryName}/{page}/{size}";

    @ApiOperation(value = "add new product")
    @PostMapping
    ApiResult<ProductDTO> add(
            @RequestParam("model") String model,
            @RequestParam("images") List<MultipartFile> file) throws JsonProcessingException;

    @ApiOperation(value = "get one product")
    @GetMapping(value = PRODUCT_ID_PATH)
    ApiResult<ProductDTO> get(@PathVariable UUID id);

    @ApiOperation(value = "search product")
    @GetMapping(value = PRODUCT_SEARCH_ID_PATH)
    ApiResult<List<SearchDTO>> search(@PathVariable String search);

    @ApiOperation(value = "get list of product")
    @GetMapping(value = GET_PRODUCT_LIST_PATH)
    ApiResult<List<ProductDTO>> getAll(@PathVariable Integer page, @PathVariable Integer size);

    @ApiOperation(value = "get list of product by category")
    @GetMapping(value = GET_PRODUCT_BY_CATEGORY_PATH)
    ApiResult<List<ProductDTO>> getAllByCategory(@PathVariable String mainCategoryName, @PathVariable String categoryName, @PathVariable Integer page, @PathVariable Integer size);

    @ApiOperation(value = "edit product")
    @PutMapping(value = PRODUCT_ID_PATH)
    ApiResult<ProductDTO> edit(
            @PathVariable UUID id,
            @RequestParam("model") String model,
            @RequestParam(value = "images", required = false) List<MultipartFile> file) throws JsonProcessingException;

    @ApiOperation(value = "edit status of product")
    @PutMapping(value = EDIT_STATUS_PATH)
    ApiResult<ProductDTO> editStatus(@PathVariable UUID id, @PathVariable UniStatusEnum status);

    @ApiOperation(value = "delete product")
    @DeleteMapping(value = PRODUCT_ID_PATH)
    ApiResult<?> delete(@PathVariable UUID id);
}
