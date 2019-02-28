package com.pehchevskip.eimtproject.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Double amount;

    @Column
    private LocalDateTime dateTime;

    @Column
    private String stripeToken;

    @Column
    private String stripeEmail;

    @OneToOne
    private AnOrder order;

}
