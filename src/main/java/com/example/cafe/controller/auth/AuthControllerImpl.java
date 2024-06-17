package com.example.cafe.controller.auth;

import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.auth.SignInDTO;
import com.example.cafe.payload.auth.TokenDTO;
import com.example.cafe.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    public ApiResult<TokenDTO> signIn(SignInDTO signDTO) {
        return authService.signIn(signDTO);
    }

    @Override
    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        return authService.refreshToken(accessToken, refreshToken);
    }
}
