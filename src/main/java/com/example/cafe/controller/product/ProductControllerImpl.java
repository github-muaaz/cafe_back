package com.example.cafe.controller.product;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.product.ProductAddDTO;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.payload.product.ProductEditDTO;
import com.example.cafe.payload.product.SearchDTO;
import com.example.cafe.services.product.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.cafe.utils.RestConstants.objectMapper;

@RestController
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController{

    private final ProductService productService;

    @Override
    public ApiResult<ProductDTO> add(String model, List<MultipartFile> files) throws JsonProcessingException {

        ProductAddDTO productAddDTO = objectMapper.readValue(model, ProductAddDTO.class);

        productAddDTO.setImages(files);

        return productService.add(productAddDTO);
    }

    @Override
    public ApiResult<ProductDTO> get(UUID id) {
        return productService.get(id);
    }

    @Override
    public ApiResult<List<SearchDTO>> search(String search) {
        return productService.search(search);
    }

    @Override
    public ApiResult<List<ProductDTO>> getAll(Integer page, Integer size) {
        return productService.getAll(page, size);
    }

    @Override
    public ApiResult<List<ProductDTO>> getAllByCategory(String mainCategoryName, String categoryName, Integer page, Integer size) {
        return productService.getAllByCategory(mainCategoryName, categoryName, page, size);
    }

    @Override
    public ApiResult<ProductDTO> edit(UUID id, String model, List<MultipartFile> files) throws JsonProcessingException {
        ProductEditDTO productEditDTO = objectMapper.readValue(model, ProductEditDTO.class);

        if (Objects.isNull(files))
            productEditDTO.setImages(new ArrayList<>());
        else
            productEditDTO.setImages(files);

        return productService.edit(id, productEditDTO);
    }

    @Override
    public ApiResult<ProductDTO> editStatus(UUID id, UniStatusEnum status) {
        return productService.editStatus(id, status);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return productService.delete(id);
    }
}
