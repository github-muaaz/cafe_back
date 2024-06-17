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
public class AuthPageDTO {

    private PageEnum name;

    @NotNull(message = "Permissionlarni berish majburiy")
    private List<PermissionDTO> permissions;

    private boolean checked;

    public static List<AuthPageDTO> map(Set<PageEnum> pages, Set<PermissionEnum> permissions) {
        return Arrays
                .stream(PageEnum.values())
                .map(pageEnum -> map(pages, pageEnum, permissions))
                .collect(Collectors.toList());
    }

    public static AuthPageDTO map(Set<PageEnum> pages, PageEnum page, Set<PermissionEnum> permissions) {
        return AuthPageDTO.builder()
                .name(page)
                .checked(pages.contains(page))
                .permissions(PermissionDTO.map(page, permissions))
                .build();
    }

    public static List<AuthPageDTO> findAllPages() {
        return Arrays
                .stream(PageEnum.values())
                .map(AuthPageDTO::setDefault)
                .collect(Collectors.toList());
    }

    private static AuthPageDTO setDefault(PageEnum page) {
        return AuthPageDTO.builder()
                .name(page)
                .checked(false)
                .permissions(PermissionDTO.findAllByPage(page))
                .build();
    }
}
