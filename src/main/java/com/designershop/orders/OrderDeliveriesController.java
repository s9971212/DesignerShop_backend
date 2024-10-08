package com.designershop.orders;

import com.designershop.carts.models.ReadCartItemResponseModel;
import com.designershop.exceptions.*;
import com.designershop.orders.models.CreateOrderDeliveryRequestModel;
import com.designershop.orders.models.CreateOrderRequestModel;
import com.designershop.orders.models.ReadOrderDeliveryResponseModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order_deliveries")
@RequiredArgsConstructor
public class OrderDeliveriesController {

    private final OrderDeliveriesService orderDeliveriesService;

    @PostMapping
    public ResponseEntity<String> createOrderDeliveries(@RequestBody @Valid CreateOrderDeliveryRequestModel request)
            throws EmptyException, UserException, OrderException {
        String address = orderDeliveriesService.createOrderDeliveries(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReadOrderDeliveryResponseModel>> readAllOrderDeliveries() throws UserException, OrderException {
        List<ReadOrderDeliveryResponseModel> response = orderDeliveriesService.readAllOrderDeliveries();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
