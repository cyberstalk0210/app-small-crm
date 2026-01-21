package org.example.appsmallcrm.service;

import org.example.appsmallcrm.dto.LoginDTO;
import org.example.appsmallcrm.dto.TokenDTO;
import org.example.appsmallcrm.entity.User;
import org.example.appsmallcrm.repo.UserRepository;
import org.example.appsmallcrm.security.JwtService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public record AuthService(
        @Lazy AuthenticationManager authenticationManager,
        UserRepository userRepository,
        JwtService jwtService
) {

    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                ));

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        TokenDTO tokenDTO = TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ResponseEntity.ok(tokenDTO);
    }
}
