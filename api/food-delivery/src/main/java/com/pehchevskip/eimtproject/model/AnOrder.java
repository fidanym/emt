package com.pehchevskip.eimtproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class AnOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDateTime dateTime;

    @Column
    private String address;

    @Column
    private Double total;

    @Enumerated
    private OrderStatus status = OrderStatus.PENDING;

    @OneToOne
    @JsonIgnore
    private Payment payment;

    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    private User user;

}
