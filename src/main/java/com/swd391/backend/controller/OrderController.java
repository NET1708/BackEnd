package com.swd391.backend.controller;

import com.swd391.backend.entity.Order;
import com.swd391.backend.request.CreateOrder;
import com.swd391.backend.request.GetDetailRequest;
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

    @PostMapping("/create/cart")
    public ResponseEntity<Order> CreateCart(@RequestBody CreateOrder order, @RequestHeader String token){

        return ResponseEntity.ok(orderService.CreateOrderCart(order, jwtService.extractUsername(token)));
    }

    @GetMapping("/get-cart")
    public ResponseEntity<Object> GetOrderCart(@RequestHeader String token){

        return ResponseEntity.ok(orderService.GetOrderCar(jwtService.extractUsername(token)));
    }

    @PostMapping("/get-detail")
    public ResponseEntity<Object> GetOrderDetail(@RequestBody GetDetailRequest orderID, @RequestHeader String token){

        return ResponseEntity.ok(orderService.GetOrderDetail(orderID.getOrderID(), jwtService.extractUsername(token)));
    }
    @PostMapping("/pay")
    public ResponseEntity<Object> PayOrder(@RequestBody GetDetailRequest orderID, @RequestHeader String token){

        return ResponseEntity.ok(orderService.PayOrder(orderID.getOrderID(), jwtService.extractUsername(token)));
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> DeleteOrder(@RequestBody GetDetailRequest orderID, @RequestHeader String token){
        orderService.DeleteOrder(orderID.getOrderID(), jwtService.extractUsername(token));
        return ResponseEntity.ok("success");
    }

    @GetMapping("/handle-payment")
    public ResponseEntity<Object> HandleOrderPayment(@RequestParam String orderID, @RequestParam int courseID, @RequestParam String token){
        orderService.HandleOrderPayment(orderID, courseID, jwtService.extractUsername(token));
        return ResponseEntity.ok("success");
    }
}
