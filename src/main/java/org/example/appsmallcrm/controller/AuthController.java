package org.example.appsmallcrm.controller;

import lombok.RequiredArgsConstructor;
import org.example.appsmallcrm.dto.ApiResponse;
import org.example.appsmallcrm.dto.LoginDTO;
import org.example.appsmallcrm.dto.TokenDTO;
import org.example.appsmallcrm.dto.UserDTO;

import org.example.appsmallcrm.service.AuthService;
import org.example.appsmallcrm.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO userDTO) {
        return authService.login(userDTO);
    }

    @GetMapping("/me")
    public ApiResponse<UserDTO> me() {
        return userService.me();
    }
}
