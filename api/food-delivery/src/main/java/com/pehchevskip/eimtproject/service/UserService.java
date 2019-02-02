package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.Role;
import com.pehchevskip.eimtproject.model.ShoppingCart;
import com.pehchevskip.eimtproject.model.User;
import com.pehchevskip.eimtproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    public User save(User user) {
        return userRepository.save(user);
    }

    public long deleteByUsername(String username) {
        return userRepository.deleteByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUserWithRole(String username, String email, String password, String firstName, String lastName, String address, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setRole(role);
        user.setShoppingCart(new ShoppingCart());
        return user;
    }

}
