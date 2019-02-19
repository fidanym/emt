package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.*;
import com.pehchevskip.eimtproject.service.AnOrderService;
import com.pehchevskip.eimtproject.service.ItemService;
import com.pehchevskip.eimtproject.service.OrderItemService;
import com.pehchevskip.eimtproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private AnOrderService orderService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setShoppingCart(new ShoppingCart());
        userService.save(user);
    }

    @GetMapping("/me")
    public ResponseEntity<User> me() {
        Optional<User> user = userService.findUserByAuth(SecurityContextHolder.getContext().getAuthentication());
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/delete")
    public Long deleteUser(@RequestParam String username) {
        return userService.deleteByUsername(username);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/shoppingCart")
    public ShoppingCart getShoppingCart(@RequestParam String username) throws InterruptedException {
        Thread.sleep(1500);
        Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent()) {
            return new ShoppingCart();
        }
        return user.get().getShoppingCart();
    }

    @GetMapping("/makeOrder")
    public ResponseEntity<AnOrder> checkout(@RequestParam String username) {
        Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ShoppingCart shoppingCart = user.get().getShoppingCart();
        List<OrderItem> orderItems = shoppingCart.getOrderItems();

        if (orderItems.size() == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AnOrder order = new AnOrder();
        order.setDateTime(LocalDateTime.now());
        order.setOrderItems(new ArrayList<>(orderItems));
        order.setTotal(orderItems.stream()
                .mapToDouble(oi -> oi.getQuantity() * oi.getItem().getPrice())
                .sum());
        order.setUser(user.get());
        order.setAddress(user.get().getAddress());

        shoppingCart.getOrderItems().clear();
        userService.save(user.get());
        return new ResponseEntity<>(orderService.makeOrder(order), HttpStatus.OK);
    }

}
