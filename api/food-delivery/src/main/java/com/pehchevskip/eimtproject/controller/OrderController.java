package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.AnOrder;
import com.pehchevskip.eimtproject.service.AnOrderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void pay(@RequestParam("id") Long orderId) {
        orderService.pay(orderId);
    }

    @GetMapping("/delivering")
    public void delivering(@RequestParam("id") Long orderId) {
        orderService.delivering(orderId);
    }

    @GetMapping("/deliver")
    public void deliver(@RequestParam("id") Long orderId) {
        orderService.deliver(orderId);
    }

}
