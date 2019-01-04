package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.OrderItem;
import com.pehchevskip.eimtproject.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

}
