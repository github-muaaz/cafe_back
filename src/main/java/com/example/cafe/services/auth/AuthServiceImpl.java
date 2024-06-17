package com.example.cafe.services.auth;

import com.example.cafe.entity.User;
import com.example.cafe.entity.enums.PageEnum;
import com.example.cafe.exceptions.RestException;
import com.example.cafe.payload.api.ApiResult;
import com.example.cafe.payload.auth.SignInDTO;
import com.example.cafe.payload.auth.TokenDTO;
import com.example.cafe.repository.UserRepository;
import com.example.cafe.security.JwtTokenProvider;
import com.example.cafe.utils.MessageConstants;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthServiceImpl(JwtTokenProvider jwtTokenProvider,
                           @Lazy AuthenticationManager authenticationManager,
                           UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(username));
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public ApiResult<TokenDTO> signIn(SignInDTO signInDTO) {
        TokenDTO tokenDTO = generateToken(signInDTO.getEmail(), signInDTO.getPassword());

        return ApiResult.successResponse(tokenDTO, MessageConstants.SUCCESSFULLY_TOKEN_GENERATED);
    }

    @Override
    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        accessToken = accessToken.substring(accessToken.indexOf("Bearer") + 6).trim();

        try {
            jwtTokenProvider.checkToken(accessToken, true);
        } catch (ExpiredJwtException ex) {
            try {
                String userId = jwtTokenProvider.getUserIdFromToken(refreshToken, false);
                User user = getUserById(UUID.fromString(userId)).orElseThrow(() -> RestException.restThrow("Token not valid"));

                if (!user.isEnabled()
                        || !user.isAccountNonExpired()
                        || !user.isAccountNonLocked()
                        || !user.isCredentialsNonExpired())
                    throw RestException.restThrow("USER_PERMISSION_RESTRICTION", HttpStatus.UNAUTHORIZED);


                LocalDateTime tokenIssuedAt = LocalDateTime.now();
                String newAccessToken = jwtTokenProvider.generateAccessToken(user, Timestamp.valueOf(tokenIssuedAt));
                String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

                user.setTokenIssuedAt(tokenIssuedAt);
                userRepository.save(user);

                TokenDTO tokenDTO = TokenDTO.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                return ApiResult.successResponse(tokenDTO);
            } catch (Exception e) {
                throw RestException.restThrow("REFRESH_TOKEN_EXPIRED", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            throw RestException.restThrow("WRONG_ACCESS_TOKEN", HttpStatus.UNAUTHORIZED);
        }

        throw RestException.restThrow("ACCESS_TOKEN_NOT_EXPIRED", HttpStatus.UNAUTHORIZED);
    }

    private TokenDTO generateToken(String email, String password) {
        User user;
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            user = (User) authentication.getPrincipal();
        } catch (DisabledException | LockedException | CredentialsExpiredException disabledException) {
            throw RestException.restThrow(MessageConstants.USER_NOT_FOUND_OR_DISABLED, HttpStatus.FORBIDDEN);
        } catch (BadCredentialsException | UsernameNotFoundException badCredentialsException) {
            throw RestException.restThrow(MessageConstants.LOGIN_OR_PASSWORD_ERROR, HttpStatus.FORBIDDEN);
        }

        LocalDateTime tokenIssuedAt = LocalDateTime.now();
        String accessToken = jwtTokenProvider.generateAccessToken(user, Timestamp.valueOf(tokenIssuedAt));
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        user.setTokenIssuedAt(tokenIssuedAt);
        userRepository.save(user);

        return TokenDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .defaultPage(getDefaultPage(user))
                .build();
    }

    private PageEnum getDefaultPage(User user) {
        if (Objects.isNull(user.getRole()))
            return null;

        return user.getRole().getDefaultPage();
    }
}
