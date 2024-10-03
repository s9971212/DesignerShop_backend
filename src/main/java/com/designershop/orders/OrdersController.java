package com.designershop.orders;

import com.designershop.exceptions.*;
import com.designershop.orders.models.CreateOrderRequestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid CreateOrderRequestModel request) throws EmptyException, UserException, ProductException, CartException, OrderException {
        String form = ordersService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(form);
    }
}
