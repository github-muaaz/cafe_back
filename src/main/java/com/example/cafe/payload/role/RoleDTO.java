package com.example.cafe.payload.role;

import com.example.cafe.entity.Role;
import com.example.cafe.entity.enums.PageEnum;
import com.example.cafe.entity.enums.PermissionEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Builder;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {

    private UUID id;

    private String name;

    private String description;

    private PageEnum defaultPage;

    private Set<PermissionEnum> permissions;

    private List<AuthPageDTO> pages;


    public static List<RoleDTO> map(List<Role> roles) {
        return roles.stream()
                .map(RoleDTO::map)
                .collect(Collectors.toList());
    }

    public static RoleDTO map(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .defaultPage(role.getDefaultPage())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions())
                .pages(AuthPageDTO.map(role.getPages(), role.getPermissions()))
                .build();
    }
}
