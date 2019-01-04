package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.AnOrder;
import com.pehchevskip.eimtproject.repository.AnOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnOrderService {

    @Autowired
    private AnOrderRepository anOrderRepository;

    public AnOrder checkout(AnOrder order) {
        order.setIsPaid(true);
        return anOrderRepository.save(order);
    }

}
