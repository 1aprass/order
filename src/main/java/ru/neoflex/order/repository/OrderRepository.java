package ru.neoflex.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.neoflex.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}