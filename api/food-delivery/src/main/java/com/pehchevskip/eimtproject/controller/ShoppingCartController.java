package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.ShoppingCart;
import com.pehchevskip.eimtproject.model.User;
import com.pehchevskip.eimtproject.service.ShoppingCartService;
import com.pehchevskip.eimtproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add-item")
    public ShoppingCart addItem(
            @RequestParam String username,
            @RequestParam Long itemId,
            @RequestParam int quantity) {
        return shoppingCartService.addItem(username, itemId, quantity);
    }

    @PostMapping("/remove-item")
    public ResponseEntity<Boolean> removeItem(@RequestParam String username, @RequestParam Long itemId) {
        if (shoppingCartService.removeItem(username, itemId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/decrease-qty")
    public ResponseEntity<Boolean> decreaseQty(
            @RequestParam String username,
            @RequestParam Long itemId,
            @RequestParam int quantity) {
        if (shoppingCartService.decreaseQty(username, itemId, quantity)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my")
    public ResponseEntity<ShoppingCart> myCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Optional<User> user = userService.findByUsername(currentUsername);

        if (!user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user.get().getShoppingCart(), HttpStatus.OK);
    }

}
