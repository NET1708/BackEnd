package com.swd391.backend.controller;

import com.swd391.backend.entity.Order;
import com.swd391.backend.request.CreateOrder;
import com.swd391.backend.request.GetDetailRequest;
import com.swd391.backend.service.ExternalApiService;
import com.swd391.backend.service.JwtService;
import com.swd391.backend.service.OrderService;
import com.swd391.backend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Order", description = "Order management APIs")
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000, https://api.ani-testlab.edu.vn")
public class OrderController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ExternalApiService externalApiService;

//    @PostMapping("/create/cart")
//    public ResponseEntity<Order> CreateCart(@RequestBody CreateOrder order, @RequestHeader String token){
//
//        return ResponseEntity.ok(orderService.CreateOrderCart(order, jwtService.extractUsername(token)));
//    }

    @GetMapping("/get-cart")
    public ResponseEntity<Object> GetOrderCart(@RequestHeader String token){

        return ResponseEntity.ok(orderService.GetOrderCar(jwtService.extractUsername(token)));
    }

    @PostMapping("/get-detail")
    public ResponseEntity<Object> GetOrderDetail(@RequestBody GetDetailRequest orderID, @RequestHeader String token){

        return ResponseEntity.ok(orderService.GetOrderDetail(orderID.getOrderID(), jwtService.extractUsername(token)));
    }

    @GetMapping("/handle-payment")
    public ResponseEntity<?> HandleOrderPayment(@RequestParam String orderID, @RequestParam int courseID, @RequestParam String token){
        ResponseEntity<?> response = orderService.HandleOrderPayment(orderID, courseID, token);
        return response;
    }

    @GetMapping("/list-enroll")
    public ResponseEntity<?> ListEnroll(@RequestParam String token){
        return ResponseEntity.ok(orderService.ListEnrollCourse(token));
    }
}
