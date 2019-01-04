package com.pehchevskip.eimtproject.controller;

import com.pehchevskip.eimtproject.model.Item;
import com.pehchevskip.eimtproject.service.CompanyService;
import com.pehchevskip.eimtproject.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    public Item createItem(
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Long companyId) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setCompany(companyService.findById(companyId).orElse(null)); // throw an exception here ?
        return itemService.save(item);
    }

    @GetMapping("/byCompanyId")
    public List<Item> findByCompanyId(@RequestParam Long companyId) {
        return itemService.findByCompanyId(companyId);
    }

}
