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

    public Item update(Item item) {
        if (item.getId() == null || item.getCompany() == null) {
            return null;
        }

        Optional<Item> found = findById(item.getId());

        if (!found.isPresent()) {
            return null;
        }

        found.get().setName(item.getName());
        found.get().setDescription(item.getDescription());
        found.get().setPrice(item.getPrice());
        found.get().setCompany(item.getCompany());

        return save(found.get());
    }
}
