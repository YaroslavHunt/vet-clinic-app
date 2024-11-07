package com.vetclinic.vetclinicapp.controller;

import com.vetclinic.vetclinicapp.dto.auth.AuthRequestDTO;
import com.vetclinic.vetclinicapp.dto.auth.AuthResponseDTO;
import com.vetclinic.vetclinicapp.dto.auth.SignUpRequestDTO;
import com.vetclinic.vetclinicapp.dto.auth.SignUpResponseDTO;
import com.vetclinic.vetclinicapp.service.auth.UserService;
import com.vetclinic.vetclinicapp.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody @Valid SignUpRequestDTO dto) {
        SignUpResponseDTO signUpResponseDto = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponseDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> signIn(@RequestBody @Valid AuthRequestDTO dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                dto.getUsername(),
                dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (authentication.isAuthenticated()) {
            UserDetails user = userService.loadUserByUsername(dto.getUsername());
            String accessToken = jwtUtil.generateAccessToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);
            return ResponseEntity.ok(
                    AuthResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
