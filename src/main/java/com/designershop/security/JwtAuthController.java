package com.designershop.security;

import com.designershop.exceptions.PasswordExpiredException;
import com.designershop.exceptions.UserException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class JwtAuthController {

    private final JwtAuthService jwtAuthService;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody Map<String, Object> request) throws UserException, PasswordExpiredException, MessagingException {
        String token = jwtAuthService.auth(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }
}
