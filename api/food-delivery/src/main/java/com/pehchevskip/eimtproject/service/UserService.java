package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.Role;
import com.pehchevskip.eimtproject.model.ShoppingCart;
import com.pehchevskip.eimtproject.model.User;
import com.pehchevskip.eimtproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        if (user.getId() == null && user.getUsername() == null) {
            return null;
        }

        Optional<User> found;

        if (user.getId() != null) {
            found = findById(user.getId());
        } else {
            found = findByUsername(user.getUsername());
        }

        if (user.getFirstName() != null) {
            found.get().setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null) {
            found.get().setLastName(user.getLastName());
        }

        if (user.getAddress() != null) {
            found.get().setAddress(user.getAddress());
        }

        if (user.getPhone() != null) {
            found.get().setPhone(user.getPhone());
        }

        if (user.getRole() != null) {
            found.get().setRole(user.getRole());
            if (!user.getRole().equals(Role.ADMIN)) {
                found.get().setCompany(null);
            }
        }

        if (user.getCompany() != null && found.get().getRole().equals(Role.ADMIN)) {
            found.get().setCompany(user.getCompany());
        }

        return save(found.get());
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

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByAuth(Authentication auth) {
        return findByUsername(auth.getName());
    }

    public User createUserWithRole(String username, String email, String password, String firstName, String lastName, String address, String phone, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhone(phone);
        user.setRole(role);
        user.setShoppingCart(new ShoppingCart());
        return user;
    }

    public boolean checkIfUserIsAdminOrSuperAdmin(User user) {
        return user.getRole().equals(Role.ADMIN)
                || user.getRole().equals(Role.SUPER_ADMIN);
    }

}
