package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.Item;
import com.pehchevskip.eimtproject.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> findByCompanyId(Long companyId) {
        return itemRepository.findItemsByCompanyId(companyId);
    }

}
