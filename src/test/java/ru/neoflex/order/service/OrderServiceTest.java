package ru.neoflex.order.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neoflex.order.dto.OrderDto;
import ru.neoflex.order.enums.OrderStatus;
import ru.neoflex.order.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderRepository repository;

    @Test
    void testCreate() {
        OrderDto dto = new OrderDto(null, "Phone", 1, OrderStatus.CREATED);
        OrderDto saved = service.create(dto);

        assertNotNull(saved.getId());
        assertEquals("Phone", saved.getProduct());
        assertEquals(1, saved.getAmount());
        assertEquals(OrderStatus.CREATED, saved.getStatus());
    }

    @Test
    void testGet() {
        OrderDto dto = new OrderDto(null, "Laptop", 2, OrderStatus.CREATED);
        OrderDto saved = service.create(dto);
        OrderDto found = service.get(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals("Laptop", found.getProduct());
    }

    @Test
    void testGetNotFound() {
        assertThrows(RuntimeException.class, () -> service.get(999L));
    }

    @Test
    void testUpdate() {
        OrderDto dto = new OrderDto(null, "Book", 1, OrderStatus.CREATED);
        OrderDto saved = service.create(dto);
        OrderDto updateDto = new OrderDto(saved.getId(), "Updated Book", 3, OrderStatus.PENDING);
        OrderDto updated = service.update(saved.getId(), updateDto);

        assertEquals("Updated Book", updated.getProduct());
        assertEquals(3, updated.getAmount());
        assertEquals(OrderStatus.PENDING, updated.getStatus());
    }

    @Test
    void testUpdateNotFound() {
        OrderDto updateDto = new OrderDto(999L, "None", 1, OrderStatus.CREATED);
        assertThrows(RuntimeException.class, () -> service.update(999L, updateDto));
    }

    @Test
    void testDelete() {
        OrderDto dto = new OrderDto(null, "Tablet", 1, OrderStatus.CREATED);
        OrderDto saved = service.create(dto);
        service.delete(saved.getId());
        assertFalse(repository.existsById(saved.getId()));
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(RuntimeException.class, () -> service.delete(999L));
    }
}