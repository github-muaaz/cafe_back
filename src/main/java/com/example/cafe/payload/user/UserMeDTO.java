package com.example.cafe.payload.user;

import com.example.cafe.entity.User;
import com.example.cafe.entity.enums.PageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Builder;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMeDTO {

    private UUID id;

    private String firstname;

    private String lastname;

    private String email;

    private boolean enabled;

    private Set<PageEnum> pages;

    private String role;

    public static UserMeDTO mapUserMeDTO(User user) {
        if (Objects.isNull(user))
            return null;

        return UserMeDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getUsername())
                .pages(getPages(user))
                .enabled(user.isEnabled())
                .role(getRoleName(user))
                .build();
    }

    private static String getRoleName(User user) {
        if (Objects.isNull(user.getRole()))
            return null;

        return user.getRole().getName();
    }

    private static Set<PageEnum> getPages(User user) {
        if (Objects.isNull(user.getRole()))
            return null;

        Set<PageEnum> pages = user.getRole().getPages();
        if (pages == null)
            return null;

        List<PageEnum> pageList = new ArrayList<>(pages);

        Collections.sort(pageList);

        return new LinkedHashSet<>(pageList);
    }
}