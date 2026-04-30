package ru.neoflex.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.order.dto.OrderDto;
import ru.neoflex.order.entity.Order;
import ru.neoflex.order.enums.OrderStatus;
import ru.neoflex.order.repository.OrderRepository;
import ru.neoflex.order.service.OrderService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Transactional
    @Override
    public OrderDto create(OrderDto dto) {
        log.trace(">> create");
        log.debug("Input: {}", dto);

        if (dto.getProduct() == null) {
            log.warn("Product is null");
        }

        OrderStatus status = dto.getStatus() != null ? dto.getStatus() : OrderStatus.CREATED;
        Order order = new Order(null, dto.getProduct(), dto.getAmount(), status);
        Order saved = repository.save(order);
        log.info("Order created: {}", saved);
        log.trace("<< create");

        return new OrderDto(saved.getId(), saved.getProduct(), saved.getAmount(), saved.getStatus());
    }

    @Transactional
    @Override
    public OrderDto get(Long id) {
        log.debug("Get order: {}", id);

        Order order = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found: {}", id);
                    return new RuntimeException("Order not found with id: " + id);
                });
        return new OrderDto(order.getId(), order.getProduct(), order.getAmount(), order.getStatus());
    }

    @Transactional
    @Override
    public OrderDto update(Long id, OrderDto dto) {
        log.debug("Update order {}", id);
        Order existingOrder = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not exists: {}", id);
                    return new RuntimeException("Order not found with id: " + id);
                });

        if (dto.getProduct() != null) {
            existingOrder.setProduct(dto.getProduct());
        }
        if (dto.getAmount() != null) {
            existingOrder.setAmount(dto.getAmount());
        }
        if (dto.getStatus() != null) {
            existingOrder.setStatus(dto.getStatus());
        }

        Order updated = repository.save(existingOrder);
        log.info("Order updated: {}", updated);
        return new OrderDto(updated.getId(), updated.getProduct(), updated.getAmount(), updated.getStatus());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        log.debug("Delete order {}", id);

        if (!repository.existsById(id)) {
            log.error("Order not exists: {}", id);
            throw new RuntimeException("Order not found with id: " + id);
        }

        repository.deleteById(id);
        log.info("Order deleted: {}", id);
    }
}