package com.example.cafe.payload.auth;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class SignInDTO {

    @Email(message = "INCORRECT EMAIL FORMAT")
    @NotBlank(message = "MUST NOT BE BLANK EMAIL")
    private String email;

    @NotBlank(message = "MUST NOT BE BLANK PASSWORD")
    private String password;
}
