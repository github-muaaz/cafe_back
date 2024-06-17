package com.example.cafe.payload.table;

import com.example.cafe.entity.Tables;
import com.example.cafe.entity.enums.TableEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TableDTO {

    private UUID id;

    private String name;

    private TableEnum status;

    public static TableDTO map(Tables table) {
        return TableDTO.builder()
                .id(table.getId())
                .name(table.getName())
                .status(table.getStatus())
                .build();
    }

    public static List<TableDTO> map(List<Tables> tables) {
        return tables.stream()
                .map(TableDTO::map)
                .collect(Collectors.toList());
    }
}
