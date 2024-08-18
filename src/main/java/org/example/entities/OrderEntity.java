package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "discount")
    private int discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private OrderStatusEntity status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "service_order",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "service_id") }
    )
    private Set<ServicesEntity> services = new HashSet<>();
}
