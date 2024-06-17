package com.example.cafe.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

//import static com.example.cafe.entity.enums.PageEnum.ROLE;
//import static com.example.cafe.entity.enums.PageEnum.USERS;

@AllArgsConstructor
@Getter
public enum PermissionEnum implements GrantedAuthority {
//    ADD_ROLE(ROLE),
//    GET_ROLES(ROLE),
//    GET_ROLE(ROLE),
//    EDIT_ROLE(ROLE),
//    DELETE_ROLE(ROLE),
//
//    ADD_USER(USERS),
//    GET_USERS(USERS),
//    GET_USER(USERS),
//    EDIT_USER(USERS),
//    DELETE_USER(USERS),
    ;
    private final PageEnum page;

    @Override
    public String getAuthority() {
        return name();
    }
}
