package com.pehchevskip.eimtproject.model;

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
    private Boolean isPaid = false;

    @Column
    private Double total;

    @OneToMany
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    private User user;

}
