package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.AnOrder;
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

    @GetMapping("/putInProgress")
    public ResponseEntity<Boolean> putInProgress(@RequestParam("id") Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        Optional<User> user = userService.findByUsername(currentPrincipalName);

        if (!user.get().getRole().equals(Role.SUPER_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (orderService.putInProgress(orderId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/delivering")
    public ResponseEntity<Boolean> delivering(@RequestParam("id") Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        Optional<User> user = userService.findByUsername(currentPrincipalName);

        if (!user.get().getRole().equals(Role.SUPER_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (orderService.delivering(orderId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/deliver")
    public ResponseEntity<Boolean> deliver(@RequestParam("id") Long orderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        Optional<User> user = userService.findByUsername(currentPrincipalName);

        if (!user.get().getRole().equals(Role.SUPER_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (orderService.deliver(orderId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
