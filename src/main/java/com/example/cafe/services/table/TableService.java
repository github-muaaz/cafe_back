package com.example.cafe.services.table;

import com.example.cafe.entity.enums.TableEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.payload.table.TableAddEditDTO;
import com.example.cafe.payload.table.TableDTO;

import java.util.List;
import java.util.UUID;

public interface TableService {
    ApiResult<TableDTO> add(TableAddEditDTO table);

    ApiResult<TableDTO> get(UUID id);

    ApiResult<List<TableDTO>> getAll(Integer page, Integer size);

    ApiResult<List<TableDTO>> getAll();

    ApiResult<TableDTO> editStatus(UUID id, TableEnum status);

    ApiResult<TableDTO> edit(UUID id, TableAddEditDTO table);

    ApiResult<?> delete(UUID id);

}
