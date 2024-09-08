package com.designershop.users;

import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.UserException;
import com.designershop.users.models.PasswordForgotRequestModel;
import com.designershop.users.models.PasswordForgotSendEmailRequestModel;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password_forgot")
@RequiredArgsConstructor
public class PasswordForgotController {

    private final PasswordForgotService passwordForgotService;

    @PostMapping
    public ResponseEntity<String> passwordForgotSendEmail(@RequestBody @Valid PasswordForgotSendEmailRequestModel request)
            throws EmptyException, UserException, MessagingException {
        String account = passwordForgotService.passwordForgotSendEmail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping
    public ResponseEntity<String> passwordForgot(@RequestParam String token, @RequestBody @Valid PasswordForgotRequestModel request)
            throws EmptyException, UserException {
        String account = passwordForgotService.passwordForgot(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }
}
