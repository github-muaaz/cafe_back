package com.example.cafe.payload.user;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class UserAddEditDTO {

    private UUID id;

    @NotBlank(message = "MUST_NOT_BE_BLANK_FIRSTNAME")
    private String firstname;

    private String lastname;

    @Email(message = "INCORRECT EMAIL FORMAT")
    @NotBlank(message = "MUST_NOT_BE_BLANK_EMAIL")
    private String email;

    @NotBlank(message = "MUST_NOT_BE_BLANK_PASSWORD")
    private String password;

    @NotNull(message = "MUST NOT BE NULL ROLE")
    private UUID roleId;

    private Boolean enabled;
}
