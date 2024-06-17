package com.example.cafe.payload.role;

import com.example.cafe.entity.enums.PageEnum;
import com.example.cafe.entity.enums.PermissionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionDTO {

    @NotNull
    private PermissionEnum name;

    private boolean checked;

    @NotNull
    private PageEnum pageName;

    public static List<PermissionDTO> map(PageEnum page, Set<PermissionEnum> permissions) {
        return Arrays
                .stream(PermissionEnum.values())
                .filter(permission -> permission.getPage() == page)
                .map(permissionEnum -> map(permissionEnum, permissions))
                .collect(Collectors.toList());
    }

    public static PermissionDTO map(PermissionEnum permission, Set<PermissionEnum> permissions) {
        return PermissionDTO.builder()
                .name(permission)
                .checked(permissions.contains(permission))
                .pageName(permission.getPage())
                .build();
    }


    public static List<PermissionDTO> findAllByPage(PageEnum page) {
        return Arrays
                .stream(PermissionEnum.values())
                .filter(permission -> permission.getPage() == page)
                .map(PermissionDTO::setDefault)
                .collect(Collectors.toList());
    }

    private static PermissionDTO setDefault(PermissionEnum permission) {
        return PermissionDTO.builder()
                .checked(false)
                .name(permission)
                .pageName(permission.getPage())
                .build();
    }
}
