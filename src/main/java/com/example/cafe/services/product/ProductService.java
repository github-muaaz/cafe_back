package com.example.cafe.services.product;

import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.product.ProductAddDTO;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.payload.product.ProductEditDTO;
import com.example.cafe.payload.product.SearchDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ApiResult<ProductDTO> add(ProductAddDTO product);

    ApiResult<ProductDTO> get(UUID id);

    ApiResult<List<SearchDTO>> search(String search);

    ApiResult<List<ProductDTO>> getAll(Integer page, Integer size);

    ApiResult<List<ProductDTO>> getAllByCategory(String mainCategoryName, String categoryName, Integer page, Integer size);

    ApiResult<ProductDTO> edit(UUID id, ProductEditDTO productDTO);

    ApiResult<ProductDTO> editStatus(UUID id, UniStatusEnum status);

    ApiResult<?> delete(UUID id);
}
