package com.pehchevskip.eimtproject.repository;


import com.pehchevskip.eimtproject.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
