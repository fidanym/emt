package com.pehchevskip.eimtproject.repository;

import com.pehchevskip.eimtproject.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
