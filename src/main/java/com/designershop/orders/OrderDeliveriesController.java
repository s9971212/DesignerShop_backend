package com.designershop.orders;

import com.designershop.carts.models.UpdateCartItemRequestModel;
import com.designershop.exceptions.*;
import com.designershop.orders.models.CreateOrderDeliveryRequestModel;
import com.designershop.orders.models.ReadOrderDeliveryResponseModel;
import com.designershop.orders.models.UpdateOrderDeliveryRequestModel;
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
    public ResponseEntity<String> createOrderDelivery(@RequestBody @Valid CreateOrderDeliveryRequestModel request)
            throws EmptyException, UserException, OrderException {
        String address = orderDeliveriesService.createOrderDelivery(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReadOrderDeliveryResponseModel>> readAllOrderDelivery() throws UserException {
        List<ReadOrderDeliveryResponseModel> response = orderDeliveriesService.readAllOrderDelivery();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadOrderDeliveryResponseModel> readOrderDelivery(@PathVariable String id)
            throws UserException, OrderException {
        ReadOrderDeliveryResponseModel response = orderDeliveriesService.readOrderDelivery(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ReadOrderDeliveryResponseModel> readOrderDeliveryByIsDefault()
            throws UserException, OrderException {
        ReadOrderDeliveryResponseModel response = orderDeliveriesService.readOrderDeliveryByIsDefault();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrderDelivery(@PathVariable String id, @RequestBody @Valid UpdateOrderDeliveryRequestModel request)
            throws EmptyException, UserException, OrderException {
        String address = orderDeliveriesService.updateOrderDelivery(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDelivery(@PathVariable String id) throws UserException, OrderException {
        String userId = orderDeliveriesService.deleteOrderDelivery(id);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }
}
