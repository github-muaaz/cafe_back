package com.example.cafe.payload.role;

import com.example.cafe.entity.enums.PageEnum;
import com.example.cafe.entity.enums.PermissionEnum;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
public class RoleAddEditDTO {

    private UUID id;

    @NotBlank(message = "MUST_NOT_BE_BLANK_NAME")
    private String name;

    private String description;

    @NotNull(message = "MUST_NOT_BE_NULL_DEFAULT_PAGE")
    private PageEnum defaultPage;

    @NotNull(message = "MUST_NOT_BE_NULL_PERMISSIONS")
    private Set<PermissionEnum> permissions;

    @NotNull(message = "MUST_NOT_BE_NULL_PAGES")
    private Set<PageEnum> pages;
}
