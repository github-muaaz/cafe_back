package com.example.cafe.services.table;

import com.example.cafe.entity.Order;
import com.example.cafe.entity.Product;
import com.example.cafe.entity.Tables;
import com.example.cafe.entity.enums.OrderEnum;
import com.example.cafe.entity.enums.TableEnum;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.payload.table.TableAddEditDTO;
import com.example.cafe.payload.table.TableDTO;
import com.example.cafe.repository.OrderRepository;
import com.example.cafe.repository.TableRepository;
import com.example.cafe.utils.MessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;

    @Override
    public ApiResult<TableDTO> add(TableAddEditDTO tableAddEditDTO) {
        if (tableRepository.existsByName(tableAddEditDTO.getName()))
            throw RestException
                    .restThrow(MessageConstants.TABLE_ALREADY_EXIST, HttpStatus.CONFLICT);

        Tables table = Tables.builder()
                .name(tableAddEditDTO.getName())
                .status(TableEnum.FREE)
                .build();

        table = tableRepository.save(table);

        return ApiResult.successResponse(TableDTO.map(table), MessageConstants.SUCCESSFULLY_ADDED);
    }

    @Override
    public ApiResult<TableDTO> get(UUID id) {
        Tables table = tableRepository
                .findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.TABLE_NOT_FOUND, HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(TableDTO.map(table));
    }

    @Override
    public ApiResult<List<TableDTO>> getAll(Integer page, Integer size) {
        Page<Tables> pagedTables = tableRepository
                .findAllByDeletedOrderByUpdatedAtDesc(false, PageRequest.of(page, size));

        return ApiResult.successResponse(TableDTO.map(pagedTables.getContent()));
    }

    @Override
    public ApiResult<List<TableDTO>> getAll() {
        List<Tables> tables = tableRepository.findAll();

        return ApiResult.successResponse(TableDTO.map(tables));
    }

    @Override
    public ApiResult<TableDTO> editStatus(UUID id, TableEnum status) {
        Tables table = tableRepository.findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.TABLE_NOT_FOUND, HttpStatus.NOT_FOUND));

        table.setStatus(status);

        tableRepository.save(table);

        List<Order> orders = orderRepository.findAllByTableIdAndStatusIsNot(id, OrderEnum.PAID);

        orders = orders.stream().peek(order -> order.setStatus(OrderEnum.PAID)).collect(Collectors.toList());

        orderRepository.saveAll(orders);

        return ApiResult.successResponse(TableDTO.map(table));
    }

    @Override
    public ApiResult<TableDTO> edit(UUID id, TableAddEditDTO tableEdit) {
        Tables table = getTable(id);

        table.setName(tableEdit.getName());
        table.setStatus(tableEdit.getStatus());

        tableRepository.save(table);

        return ApiResult.successResponse(TableDTO.map(table), MessageConstants.SUCCESSFULLY_EDITED);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        getTable(id);

        tableRepository.deleteById(id);

        return ApiResult.successResponse(MessageConstants.SUCCESSFULLY_DELETED);
    }

    private Tables getTable(UUID id) {
        return tableRepository
                .findById(id)
                .orElseThrow(() ->
                        RestException
                                .restThrow(MessageConstants.TABLE_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
