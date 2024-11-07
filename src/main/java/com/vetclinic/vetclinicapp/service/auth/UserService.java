package com.vetclinic.vetclinicapp.service.auth;

import com.vetclinic.vetclinicapp.dto.auth.SignUpRequestDTO;
import com.vetclinic.vetclinicapp.dto.auth.SignUpResponseDTO;
import com.vetclinic.vetclinicapp.entity.User;
import com.vetclinic.vetclinicapp.exception.GlobalExceptionHandler;
import com.vetclinic.vetclinicapp.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new GlobalExceptionHandler
                        .CustomException(
                        String.format("User %s not found", username),
                        HttpStatus.NOT_FOUND));
    }

    public SignUpResponseDTO createUser(@Valid SignUpRequestDTO dto) {
        String password = passwordEncoder.encode(dto.getPassword());

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(password);
        user.setRole(dto.getRole());
        user.setRegisteredAt(LocalDateTime.now());
        userRepository.save(user);

        return SignUpResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .registeredAt(user.getRegisteredAt())
                .build();
    }
}
