package com.pehchevskip.eimtproject.repository;

import com.pehchevskip.eimtproject.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findItemsByCompanyId(Long companyId);

}
