package com.lockbeck.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lockbeck.config.JwtService;
import com.lockbeck.entities.token.Token;
import com.lockbeck.entities.token.TokenRepository;
import com.lockbeck.entities.token.TokenType;
import com.lockbeck.entities.user.UserEntity;
import com.lockbeck.entities.user.UserRepository;
import com.lockbeck.exceptions.BadRequestException;
import com.lockbeck.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final UserRepository userRepository;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Optional<UserEntity> byId = userRepository.findByUsernameAndDeletedFalse(request.getEmail());

        if (byId.isEmpty()) {
            throw new NotFoundException("Login xato kiritildi");
        }
        UserEntity user = byId.get();
        passwordCheck(request, user);





        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        user.setLoginDate(LocalDateTime.now());
        user.setLogoutDate(LocalDateTime.now().plusHours(2));
        repository.save(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void passwordCheck(AuthenticationRequest request, UserEntity userEntity) {
        boolean matches = passwordEncoder.matches(request.getPassword(), userEntity.getPassword());
        if (userEntity.getIsLocked().equals(Boolean.TRUE)) {
            if (userEntity.getLockedDate().after(Date.from(Instant.now()))) {
                throw new BadRequestException("user vaqtincha bloklangan keyinroq harakat qilib ko`ring");

            } else {
                userEntity.setIsLocked(Boolean.FALSE);
                userEntity.setFailAttempt(0);
                repository.save(userEntity);
            }

        }
        if (userEntity.getFailAttempt() >= 3) {
            userEntity.setIsLocked(Boolean.TRUE);
            userEntity.setLockedDate(Date.from(Instant.now().plusSeconds(60)));
            repository.save(userEntity);
            throw new BadRequestException("parol bir necha martta xato kiritildi. keyinroq harakat qilib ko'ring");
        }
        if (!matches) {
            userEntity.setFailAttempt(userEntity.getFailAttempt() + 1);
            repository.save(userEntity);
            throw new BadRequestException("Parol xato kiritildi");

        } else {
            userEntity.setFailAttempt(0);
            userEntity.setIsLocked(Boolean.FALSE);
        }
    }

    private void saveUserToken(UserEntity userEntity, String jwtToken) {
        var token = Token.builder()
                .userEntity(userEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity userEntity) {
        var validUserTokens = tokenRepository.findAllValidTokenByUserEntity(userEntity.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.repository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
