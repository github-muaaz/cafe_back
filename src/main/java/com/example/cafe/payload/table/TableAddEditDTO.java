package com.example.cafe.payload.table;

import com.example.cafe.entity.enums.TableEnum;
import lombok.Getter;

@Getter
public class TableAddEditDTO {

    private String name;

    private TableEnum status;
}
