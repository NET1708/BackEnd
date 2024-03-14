package com.swd391.backend.controller;

import com.swd391.backend.request.CreateOrder;
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
    public ResponseEntity<String> CreateCart(@RequestBody List<CreateOrder> order, @RequestHeader String token){
        orderService.CreateOrderCart(order, jwtService.extractUsername(token));
        return ResponseEntity.ok("success");
    }
    public ResponseEntity<Object> GetOrderCart(@RequestBody List<CreateOrder> order, @RequestHeader String token){

        return ResponseEntity.ok(orderService.GetOrderCar(jwtService.extractUsername(token)));
    }

}
