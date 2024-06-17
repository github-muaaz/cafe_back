package com.example.cafe.controller.table;

import com.example.cafe.entity.enums.TableEnum;
import com.example.cafe.entity.enums.UniStatusEnum;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.api.PageData;
import com.example.cafe.payload.product.ProductDTO;
import com.example.cafe.payload.table.TableAddEditDTO;
import com.example.cafe.payload.table.TableDTO;
import com.example.cafe.services.table.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TableControllerImpl implements TableController {

    private final TableService tableService;

    @Override
    public ApiResult<TableDTO> add(TableAddEditDTO tableDTO) {
        return tableService.add(tableDTO);
    }

    @Override
    public ApiResult<TableDTO> get(UUID id) {
        return tableService.get(id);
    }

    @Override
    public ApiResult<List<TableDTO>> getAll(Integer page, Integer size) {
        return tableService.getAll(page, size);
    }

    @Override
    public ApiResult<List<TableDTO>> getAll() {
        return tableService.getAll();
    }

    @Override
    public ApiResult<TableDTO> editStatus(UUID id, TableEnum status) {
        return tableService.editStatus(id, status);
    }

    @Override
    public ApiResult<TableDTO> edit(UUID id, TableAddEditDTO table) {
        return tableService.edit(id, table);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return tableService.delete(id);
    }
}
