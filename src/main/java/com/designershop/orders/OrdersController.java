package com.designershop.orders;

import com.designershop.entities.Cart;
import com.designershop.entities.Order;
import com.designershop.exceptions.*;
import com.designershop.orders.models.CreateCartItemRequestModel;
import com.designershop.orders.models.CreateOrderRequestModel;
import com.designershop.orders.models.ReadCartItemResponseModel;
import com.designershop.orders.models.UpdateCartItemRequestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody @Valid CreateOrderRequestModel request) throws EmptyException, UserException, ProductException, CartException, OrderException {
        Order order = ordersService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order.getUserId());
    }
}
