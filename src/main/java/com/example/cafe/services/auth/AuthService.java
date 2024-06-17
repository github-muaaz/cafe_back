package com.example.cafe.services.auth;

import com.example.cafe.entity.User;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.auth.SignInDTO;
import com.example.cafe.payload.auth.TokenDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface AuthService extends UserDetailsService {
    Optional<User> getUserById(UUID id);

    ApiResult<TokenDTO> signIn(SignInDTO signDTO);

    ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken);
}
