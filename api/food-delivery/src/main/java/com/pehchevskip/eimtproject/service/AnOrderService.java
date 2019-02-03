package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.AnOrder;
import com.pehchevskip.eimtproject.model.OrderStatus;
import com.pehchevskip.eimtproject.repository.AnOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnOrderService {

    @Autowired
    private AnOrderRepository anOrderRepository;

    public List<AnOrder> getOrdersForUser(String username) {
        return anOrderRepository.findAllByUser_Username(username);
    }

    public AnOrder makeOrder(AnOrder order) {
        order.setStatus(OrderStatus.NOT_PAYED);
        return anOrderRepository.save(order);
    }

    public boolean pay(Long orderId) {
        return changeOrderStatus(orderId, OrderStatus.IN_PROGRESS);
    }

    public boolean delivering(Long orderId) {
        return changeOrderStatus(orderId, OrderStatus.DELIVERING);
    }

    public boolean deliver(Long orderId) {
        return changeOrderStatus(orderId, OrderStatus.DELIVERED);
    }

    private boolean changeOrderStatus(Long orderId, OrderStatus status) {
        Optional<AnOrder> order = anOrderRepository.findById(orderId);
        if (!order.isPresent()) {
            return false;
        }
        order.get().setStatus(status);
        anOrderRepository.save(order.get());
        return true;
    }
}
