package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.*;
import com.pehchevskip.eimtproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setShoppingCart(new ShoppingCart());
        user.setRole(Role.CLIENT);
        userService.save(user);
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(@ModelAttribute User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> currentUser = userService.findByUsername(currentUsername);

        if (!currentUser.get().getRole().equals(Role.SUPER_ADMIN)
                && !(currentUser.get().getId().equals(user.getId())
                    || (currentUser.get().getUsername().equals(user.getUsername())) )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User updated = userService.update(user);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updated, HttpStatus.OK);
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
    public ResponseEntity<Long> deleteUser(@RequestParam String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);

        if (!user.get().getRole().equals(Role.SUPER_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(userService.deleteByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);

        if (!user.get().getRole().equals(Role.SUPER_ADMIN)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

}
