package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.AnOrder;
import com.pehchevskip.eimtproject.service.AnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private AnOrderService orderService;

    @GetMapping("/my")
    public List<AnOrder> my() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        return orderService.getOrdersForUser(currentPrincipalName);
    }

    @GetMapping("/pay")
    public ResponseEntity<Boolean> pay(@RequestParam("id") Long orderId) {
        if (orderService.pay(orderId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/delivering")
    public ResponseEntity<Boolean> delivering(@RequestParam("id") Long orderId) {
        if (orderService.delivering(orderId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/deliver")
    public ResponseEntity<Boolean> deliver(@RequestParam("id") Long orderId) {
        if (orderService.deliver(orderId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
