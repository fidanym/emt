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
        if (item.getId() == null) {
            return null;
        }

        Optional<Item> found = findById(item.getId());

        if (!found.isPresent()) {
            return null;
        }

        if (item.getName() != null) {
            found.get().setName(item.getName());
        }

        if (item.getDescription() != null) {
            found.get().setDescription(item.getDescription());
        }

        if (item.getPrice() != null) {
            found.get().setPrice(item.getPrice());
        }

        return save(found.get());
    }

    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
