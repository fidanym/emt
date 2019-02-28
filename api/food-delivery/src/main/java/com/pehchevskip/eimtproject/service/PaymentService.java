package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.Payment;
import com.pehchevskip.eimtproject.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

}
