package ru.neoflex.order.service;

import ru.neoflex.order.dto.OrderDto;

public interface OrderService {
    OrderDto create(OrderDto dto);
    OrderDto get(Long id);
    OrderDto update(Long id, OrderDto dto);
    void delete(Long id);
}
