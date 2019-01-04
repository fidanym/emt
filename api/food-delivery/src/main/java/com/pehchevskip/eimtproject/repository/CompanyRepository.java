package com.pehchevskip.eimtproject.repository;

import com.pehchevskip.eimtproject.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
