package com.designershop.orders;

import com.designershop.exceptions.*;
import com.designershop.orders.models.CreateOrderDeliveryRequestModel;
import com.designershop.orders.models.CreateOrderRequestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orderDeliveries")
@RequiredArgsConstructor
public class OrderDeliveriesController {

    private final OrderDeliveriesService orderDeliveriesService;

    @PostMapping
    public ResponseEntity<String> createOrderDelivery(@RequestBody @Valid CreateOrderDeliveryRequestModel request) throws EmptyException, UserException, OrderException {
        String address = orderDeliveriesService.createOrderDelivery(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }
}
