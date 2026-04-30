package ru.neoflex.order.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.neoflex.order.enums.OrderStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String product;
    private Integer amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}