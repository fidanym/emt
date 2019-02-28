package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.AnOrder;
import com.pehchevskip.eimtproject.model.OrderStatus;
import com.pehchevskip.eimtproject.model.Role;
import com.pehchevskip.eimtproject.model.User;
import com.pehchevskip.eimtproject.service.AnOrderService;
import com.pehchevskip.eimtproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private AnOrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/my")
    public List<AnOrder> my() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        return orderService.getOrdersForUser(currentPrincipalName);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnOrder>> all() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        Optional<User> user = userService.findByUsername(currentPrincipalName);

        if (!user.get().getRole().equals(Role.SUPER_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<AnOrder> checkout(@RequestParam String stripeToken, @RequestParam String stripeEmail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        Optional<User> user = userService.findByUsername(currentPrincipalName);

        AnOrder order = orderService.checkout(user.get(), stripeToken, stripeEmail);

        if (order == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/changeStatus")
    public ResponseEntity<Boolean> changeStatus(@RequestParam("id") Long orderId, @RequestParam OrderStatus status) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        Optional<User> user = userService.findByUsername(currentPrincipalName);

        if (!user.get().getRole().equals(Role.SUPER_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (orderService.changeOrderStatus(orderId, status)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
