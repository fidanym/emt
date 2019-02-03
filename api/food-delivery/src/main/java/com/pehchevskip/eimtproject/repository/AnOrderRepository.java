package com.pehchevskip.eimtproject.repository;

import com.pehchevskip.eimtproject.model.AnOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnOrderRepository extends JpaRepository<AnOrder, Long> {

    List<AnOrder> findAllByUser_Username(String username);

}
