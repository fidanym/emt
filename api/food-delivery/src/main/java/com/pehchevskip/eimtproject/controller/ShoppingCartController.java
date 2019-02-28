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
    public ResponseEntity<ShoppingCart> addItem(
            @RequestParam String username,
            @RequestParam Long itemId,
            @RequestParam int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);

        if (!user.get().getUsername().equals(username)) {
            if (!userService.checkIfUserIsAdminOrSuperAdmin(user.get())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        ShoppingCart cart = shoppingCartService.addItem(username, itemId, quantity);

        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/remove-item")
    public ResponseEntity<ShoppingCart> removeItem(@RequestParam String username, @RequestParam Long itemId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);

        if (!user.get().getUsername().equals(username)) {
            if (!userService.checkIfUserIsAdminOrSuperAdmin(user.get())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        ShoppingCart cart = shoppingCartService.removeItem(username, itemId);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/decrease-qty")
    public ResponseEntity<ShoppingCart> decreaseQty(
            @RequestParam String username,
            @RequestParam Long itemId,
            @RequestParam int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);

        if (!user.get().getUsername().equals(username)) {
            if (!userService.checkIfUserIsAdminOrSuperAdmin(user.get())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }

        ShoppingCart cart = shoppingCartService.decreaseQty(username, itemId, quantity);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my")
    public ResponseEntity<ShoppingCart> myCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Optional<User> user = userService.findByUsername(currentUsername);

        return new ResponseEntity<>(user.get().getShoppingCart(), HttpStatus.OK);
    }

}
