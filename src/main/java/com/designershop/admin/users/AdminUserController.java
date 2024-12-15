package com.designershop.admin.users;

import com.designershop.admin.users.models.AdminCreateUserRequestModel;
import com.designershop.admin.users.models.AdminReadUserResponseModel;
import com.designershop.admin.users.models.AdminUpdatePasswordRequestModel;
import com.designershop.admin.users.models.AdminUpdateUserRequestModel;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.ProductException;
import com.designershop.exceptions.UserException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid AdminCreateUserRequestModel request) throws EmptyException, UserException, MessagingException {
        String account = adminUserService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @GetMapping
    public ResponseEntity<List<AdminReadUserResponseModel>> readAllUser() {
        List<AdminReadUserResponseModel> response = adminUserService.readAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminReadUserResponseModel> readUser(@PathVariable String id) throws UserException {
        AdminReadUserResponseModel response = adminUserService.readUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestBody @Valid AdminUpdateUserRequestModel request)
            throws EmptyException, UserException {
        String account = adminUserService.updateUser(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable String id, @RequestBody @Valid AdminUpdatePasswordRequestModel request)
            throws EmptyException, UserException, MessagingException {
        String account = adminUserService.updatePassword(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) throws UserException, ProductException, MessagingException {
        String account = adminUserService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
}
