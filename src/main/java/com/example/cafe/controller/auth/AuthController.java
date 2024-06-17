package com.example.cafe.controller.auth;

import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.auth.SignInDTO;
import com.example.cafe.payload.auth.TokenDTO;
import com.example.cafe.utils.RestConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(path = AuthController.AUTH_CONTROLLER_BASE_PATH)
public interface AuthController {
    String AUTH_CONTROLLER_BASE_PATH = RestConstants.BASE_PATH + "auth";
    String SIGN_IN_PATH = "/sign-in";
    String REFRESH_TOKEN_PATH = "/refresh-token";

    @ApiOperation(value = "Sign in path")
    @PostMapping(value = SIGN_IN_PATH)
    ApiResult<TokenDTO> signIn(@Valid @RequestBody SignInDTO signInDTO);

    @ApiOperation(value = "refresh token path")
    @GetMapping(value = REFRESH_TOKEN_PATH)
    ApiResult<TokenDTO> refreshToken(@RequestHeader(value = RestConstants.AUTHENTICATION_HEADER) String accessToken,
                                     @RequestHeader(value = RestConstants.REFRESH_TOKEN_HEADER) String refreshToken);
}
