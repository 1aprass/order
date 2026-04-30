package ru.neoflex.order.dto;

import lombok.*;
import ru.neoflex.order.enums.OrderStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String product;
    private Integer amount;
    private OrderStatus status;

}
