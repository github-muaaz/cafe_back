package com.example.cafe.services.order;

import com.example.cafe.entity.*;
import com.example.cafe.entity.enums.OrderEnum;
import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.category.CategoryAddEditDTO;
import com.example.cafe.payload.category.CategoryDTO;
import com.example.cafe.payload.order.OrderAddDTO;
import com.example.cafe.payload.order.OrderDTO;
import com.example.cafe.payload.order.OrderProductAddDTO;
import com.example.cafe.payload.order.TableOrderDTO;
import com.example.cafe.payload.table.TableDTO;
import com.example.cafe.repository.CategoryRepository;
import com.example.cafe.repository.OrderRepository;
import com.example.cafe.repository.ProductRepository;
import com.example.cafe.repository.TableRepository;
import com.example.cafe.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final TableRepository tableRepository;

    @Override
    public ApiResult<OrderDTO> add(OrderAddDTO orderDTO) {
        Tables table = tableRepository.findById(orderDTO.getTableId())
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.TABLE_NOT_FOUND, HttpStatus.CONFLICT));

        Order order = Order.builder()
                .table(table)
                .orderProducts(saveOrderProducts(orderDTO.getOrderProducts()))
                .status(OrderEnum.NEW)
                .build();

        order = orderRepository.save(order);

        return ApiResult.successResponse(OrderDTO.map(order), MessageConstants.SUCCESSFULLY_ADDED);
    }

    @Override
    public ApiResult<OrderDTO> get(UUID id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(OrderDTO.map(order));
    }

    @Override
    public ApiResult<PageData<List<OrderDTO>>> getAll(Integer page, Integer size) {
        Page<Order> pagedOrders = orderRepository.findAllByDeletedOrderByUpdatedAtDesc(false, PageRequest.of(page, size));

        return ApiResult.successResponse(
                PageData
                        .generate(
                                OrderDTO.map(pagedOrders.getContent()),
                                pagedOrders.getSize(),
                                pagedOrders.getNumber(),
                                pagedOrders.getTotalPages(),
                                pagedOrders.getTotalElements()
                        ));
    }

    @Override
    public ApiResult<List<OrderDTO>> getAllForAdmin(Integer page, Integer size) {
        Page<Order> pagedOrders = orderRepository
                .findAllByDeletedOrderByUpdatedAtDesc(false, PageRequest.of(page, size));

        return ApiResult.successResponse(OrderDTO.map(pagedOrders.getContent()));
    }

    @Override
    public ApiResult<List<TableOrderDTO>> getAll(UUID tableId) {
        if (!tableRepository.existsById(tableId))
            throw RestException
                    .restThrow(MessageConstants.TABLE_NOT_FOUND, HttpStatus.NOT_FOUND);

        List<Order> orders = orderRepository.findAllByTableIdAndStatusIsNot(tableId, OrderEnum.PAID);

        return ApiResult.successResponse(TableOrderDTO.map(orders));
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

    private Category getCategory(UUID id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private List<OrderProduct> saveOrderProducts(List<OrderProductAddDTO> orderProducts) {
        if (orderProducts.isEmpty())
            throw RestException
                    .restThrow(MessageConstants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);

        List<Product> products = productRepository.findAllById(orderProducts
                .stream()
                .map(OrderProductAddDTO::getProductId)
                .collect(Collectors.toList()));

        return orderProducts.stream()
                .map(orderProduct -> OrderProduct
                        .builder()
                        .quantity(orderProduct.getQuantity())
                        .status(OrderEnum.NEW)
                        .product(findProduct(products, orderProduct))
                        .build())
                .collect(Collectors.toList());
    }

    private Product findProduct(List<Product> products, OrderProductAddDTO orderProduct) {
        return products
                .stream()
                .filter(product -> Objects.equals(product.getId(), orderProduct.getProductId()))
                .findFirst()
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

}
