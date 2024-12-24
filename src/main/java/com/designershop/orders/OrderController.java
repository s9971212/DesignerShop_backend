package com.designershop.orders;

import com.designershop.exceptions.CartException;
import com.designershop.exceptions.EmptyException;
import com.designershop.exceptions.OrderException;
import com.designershop.orders.models.CreateOrderRequestModel;
import com.designershop.orders.models.ReadOrderResponseModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{deliveryId}")
    public ResponseEntity<String> createOrder(@PathVariable String deliveryId, @RequestBody @Valid CreateOrderRequestModel request)
            throws EmptyException, CartException, OrderException {
        String form = orderService.createOrder(deliveryId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(form);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReadOrderResponseModel>> readAllOrder() throws OrderException {
        List<ReadOrderResponseModel> response = orderService.readAllOrder();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
