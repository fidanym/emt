package com.pehchevskip.eimtproject.repository;

import com.pehchevskip.eimtproject.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
