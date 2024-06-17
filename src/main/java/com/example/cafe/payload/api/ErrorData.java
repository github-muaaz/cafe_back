package com.example.cafe.payload.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData {
    //USERGA BORADIGAN XABAR
    private String message;

    //XATOLIK KODI
    private Integer errorCode;

    //QAYSI FIELD XATO EKANLIGI
    private String fieldName;

    public ErrorData(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
