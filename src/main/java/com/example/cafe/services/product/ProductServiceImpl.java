package com.example.cafe.services.product;

import com.example.cafe.entity.Category;
import com.example.cafe.entity.FileImg;
import com.example.cafe.entity.Product;
import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.product.ProductAddDTO;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.payload.product.ProductEditDTO;
import com.example.cafe.payload.product.SearchDTO;
import com.example.cafe.repository.CategoryRepository;
import com.example.cafe.repository.ProductRepository;
import com.example.cafe.services.io.IOService;
import com.example.cafe.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final IOService ioService;

    @Override
    public ApiResult<ProductDTO> add(ProductAddDTO productAddDTO) {
        if (productRepository.existsByTitle(productAddDTO.getTitle()))
            throw RestException
                    .restThrow(MessageConstants.PRODUCT_ALREADY_EXIST, HttpStatus.CONFLICT);

        if (Objects.nonNull(productAddDTO.getDescription()) && productAddDTO.getDescription().length() > 700)
            throw RestException
                    .restThrow(MessageConstants.DESCRIPTION_LENGTH_OVER_FLOW, HttpStatus.BAD_REQUEST);

        Product product = Product.builder()
                .title(productAddDTO.getTitle())
                .price(productAddDTO.getPrice())
                .description(productAddDTO.getDescription())
                .unit(productAddDTO.getUnit())
                .currency(productAddDTO.getCurrency())
                .status(UniStatusEnum.NON_ACTIVE)
                .ingredient(productAddDTO.getIngredients())
                .discount(productAddDTO.getDiscount())
                .categories(getCategories(productAddDTO.getCategoryIds()))
                .images(getAndSaveImages(productAddDTO.getImages()))
                .build();

        product = productRepository.save(product);

        return ApiResult.successResponse(ProductDTO.map(product), MessageConstants.SUCCESSFULLY_ADDED);
    }

    @Override
    public ApiResult<ProductDTO> get(UUID id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(ProductDTO.map(product));
    }

    @Override
    public ApiResult<List<SearchDTO>> search(String search) {
        List<Product> products = productRepository.findAllByTitleContainingIgnoreCase(search, PageRequest.ofSize(15));

        List<Category> categories = categoryRepository.findAllByNameContainingIgnoreCase(search, PageRequest.ofSize(5));

        List<SearchDTO> result = products.stream()
                .map(product -> SearchDTO
                        .builder()
                        .id(product.getId())
                        .name(product.getTitle())
                        .field("product")
                        .build())
                .collect(Collectors.toList());

        result.addAll(categories.stream()
                .map(category -> SearchDTO
                        .builder()
                        .id(category.getId())
                        .name(category.getName())
                        .field("category")
                        .build())
                .collect(Collectors.toList()));

        return ApiResult.successResponse(result);
    }

    @Override
    public ApiResult<List<ProductDTO>> getAll(Integer page, Integer size) {
        Page<Product> pagedProducts = productRepository.findAllByDeletedOrderByUpdatedAtDesc(false, PageRequest.of(page, size));

        return ApiResult.successResponse(ProductDTO.map(pagedProducts.getContent()));
    }

    @Override
    public ApiResult<List<ProductDTO>> getAllByCategory(String mainCategoryName, String categoryName, Integer page, Integer size) {
        Category mainCategory = categoryRepository.findByNameIgnoreCase(mainCategoryName)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.MAIN_CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));

        Category category = categoryRepository.findByNameIgnoreCase(categoryName)
                .orElse(null);

        Page<Product> pagedProducts;

        if (Objects.isNull(category))
            pagedProducts = productRepository.findByCategoriesIn(List.of(mainCategory), PageRequest.of(page, size));
        else
            pagedProducts = productRepository.findByCategoriesIn(List.of(category), PageRequest.of(page, size));

        return ApiResult.successResponse(
                ProductDTO
                        .map(pagedProducts.getContent()));
    }

    @Override
    public ApiResult<ProductDTO> edit(UUID id, ProductEditDTO productDTO) {
        Product product = getProduct(id);

        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setUnit(productDTO.getUnit());
        product.setCurrency(productDTO.getCurrency());
        product.setIngredient(productDTO.getIngredients());
        product.setDiscount(productDTO.getDiscount());
        product.setCategories(getCategories(productDTO.getCategoryIds()));
        product.setImages(saveOldNewImages(product, productDTO.getOldFiles(), productDTO.getImages()));

        productRepository.save(product);

        return ApiResult.successResponse(ProductDTO.map(product), MessageConstants.SUCCESSFULLY_EDITED);
    }

    @Override
    public ApiResult<ProductDTO> editStatus(UUID id, UniStatusEnum status) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

        product.setStatus(status);

        productRepository.save(product);

        return ApiResult.successResponse(ProductDTO.map(product));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        getProduct(id);

        productRepository.deleteById(id);

        return ApiResult.successResponse(MessageConstants.SUCCESSFULLY_DELETED);
    }

    private Product getProduct(UUID id) {
        return productRepository
                .findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private List<FileImg> getAndSaveImages(List<MultipartFile> images) {
        try {
            return ioService.upload(images);
        } catch (IOException e) {
            throw new RestException("COULD NOT SAVE IMAGE", HttpStatus.CONFLICT);
        }
    }

    private List<Category> getCategories(List<UUID> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    private List<FileImg> saveOldNewImages(Product product, Set<UUID> oldFiles, List<MultipartFile> newImages) {

        List<FileImg> images = product.getImages()
                .stream()
                .filter(img -> oldFiles.contains(img.getId()))
                .collect(Collectors.toList()); // Create a new mutable list

        List<FileImg> deletingImages = product.getImages()
                .stream()
                .filter(img -> !oldFiles.contains(img.getId()))
                .collect(Collectors.toList());

        ioService.delete(deletingImages);

        try {
            if (!Objects.isNull(oldFiles) && newImages.size() > 0)
                images.addAll(ioService
                        .upload(newImages)); // Add newly uploaded images
        } catch (IOException e) {
            throw new RestException("COULD NOT SAVE IMAGE", HttpStatus.CONFLICT);
        }

        return images;
    }
}
