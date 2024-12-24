package com.designershop.users;

import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import com.designershop.users.models.CreateUserRequestModel;
import com.designershop.users.models.ReadUserResponseModel;
import com.designershop.users.models.UpdatePasswordRequestModel;
import com.designershop.users.models.UpdateUserRequestModel;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserRequestModel request) throws EmptyException, UserException, MessagingException {
        String account = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadUserResponseModel> readUser(@PathVariable String id) throws UserException {
        ReadUserResponseModel response = userService.readUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody @Valid UpdateUserRequestModel request)
            throws EmptyException, UserException {
        String account = userService.updateUser(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable String id, @RequestBody @Valid UpdatePasswordRequestModel request)
            throws EmptyException, UserException, MessagingException {
        String account = userService.updatePassword(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) throws UserException, ProductException, MessagingException {
        String account = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/logout")).build();
    }
}
