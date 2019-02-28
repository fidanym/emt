package com.pehchevskip.eimtproject.service;

import com.pehchevskip.eimtproject.model.*;
import com.pehchevskip.eimtproject.repository.AnOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnOrderService {

    @Autowired
    private AnOrderRepository anOrderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    public Optional<AnOrder> findById(Long id) {
        return anOrderRepository.findById(id);
    }

    public List<AnOrder> getOrdersForUser(String username) {
        return anOrderRepository.findAllByUser_Username(username);
    }

    public List<AnOrder> getAllOrders() {
        return anOrderRepository.findAll();
    }

    public AnOrder checkout(User user, String stripeToken, String stripeEmail) {
        if (user == null) {
            return null;
        }

        ShoppingCart cart = user.getShoppingCart();
        List<OrderItem> orderItems = cart.getOrderItems();

        if (orderItems.isEmpty()) {
            return null;
        }

        AnOrder order = new AnOrder();
        order.setDateTime(LocalDateTime.now());
        order.setOrderItems(new ArrayList<>(orderItems));
        order.setTotal(orderItems.stream()
                .mapToDouble(oi -> oi.getQuantity() * oi.getItem().getPrice())
                .sum());
        order.setUser(user);
        order.setAddress(user.getAddress());

        cart.getOrderItems().clear();

        userService.save(user);
        AnOrder savedOrder = anOrderRepository.save(order);

        Payment payment = new Payment();
        payment.setAmount(savedOrder.getTotal());
        payment.setDateTime(LocalDateTime.now());
        payment.setOrder(savedOrder);
        payment.setStripeToken(stripeToken);
        payment.setStripeEmail(stripeEmail);

        Payment savedPayment = paymentService.save(payment);

        savedOrder.setPayment(savedPayment);

        return anOrderRepository.save(savedOrder);
    }

    public boolean changeOrderStatus(Long orderId, OrderStatus status) {
        Optional<AnOrder> order = anOrderRepository.findById(orderId);
        if (!order.isPresent()) {
            return false;
        }

        order.get().setStatus(status);
        anOrderRepository.save(order.get());
        return true;
    }

}
