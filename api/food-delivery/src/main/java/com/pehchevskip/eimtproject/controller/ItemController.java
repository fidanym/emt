package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.Item;
import com.pehchevskip.eimtproject.model.Role;
import com.pehchevskip.eimtproject.model.User;
import com.pehchevskip.eimtproject.service.CompanyService;
import com.pehchevskip.eimtproject.service.ItemService;
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
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Item> createItem(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam Long companyId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);

        if (!(user.get().getRole().equals(Role.SUPER_ADMIN)
                || (user.get().getRole().equals(Role.ADMIN)
                && companyId.equals(user.get().getCompany().getId())))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setPrice(price);
        item.setCompany(companyService.findById(companyId).get());
        return new ResponseEntity<>(itemService.save(item), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Item> updateItem(@ModelAttribute Item item) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);

        Optional<Item> found = itemService.findById(item.getId());

        if (!found.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!(user.get().getRole().equals(Role.SUPER_ADMIN)
                || (user.get().getRole().equals(Role.ADMIN)
                && found.get().getCompany().getId().equals(user.get().getCompany().getId())))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Item updated = itemService.update(item);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteItem(@RequestParam Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        Optional<User> user = userService.findByUsername(currentUsername);
        Optional<Item> item = itemService.findById(id);

        if (!item.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!(user.get().getRole().equals(Role.SUPER_ADMIN)
                || (user.get().getRole().equals(Role.ADMIN)
                && item.get().getCompany().getId().equals(user.get().getCompany().getId())))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        itemService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/byCompanyId")
    public List<Item> findByCompanyId(@RequestParam Long companyId) {
        return itemService.findByCompanyId(companyId);
    }

}
