package ru.neoflex.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.neoflex.order.dto.OrderDto;
import ru.neoflex.order.enums.OrderStatus;
import ru.neoflex.order.service.OrderService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDto testOrderDto;

    @BeforeEach
    void setUp() {
        testOrderDto = new OrderDto();
        testOrderDto.setId(1L);
        testOrderDto.setProduct("Laptop");
        testOrderDto.setAmount(2);
        testOrderDto.setStatus(OrderStatus.CREATED);
    }

    @Test
    void create_shouldReturnCreatedOrder() throws Exception {
        when(orderService.create(any(OrderDto.class))).thenReturn(testOrderDto);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrderDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.product").value("Laptop"))
                .andExpect(jsonPath("$.amount").value(2))
                .andExpect(jsonPath("$.status").value(OrderStatus.CREATED.name()));
    }

    @Test
    void get_shouldReturnOrderById() throws Exception {
        when(orderService.get(1L)).thenReturn(testOrderDto);

        mockMvc.perform(get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.product").value("Laptop"))
                .andExpect(jsonPath("$.amount").value(2))
                .andExpect(jsonPath("$.status").value(OrderStatus.CREATED.name()));
    }


    @Test
    void update_shouldReturnUpdatedOrder() throws Exception {
        OrderDto updateRequest = new OrderDto();
        updateRequest.setProduct("MacBook");
        updateRequest.setAmount(1);
        updateRequest.setStatus(OrderStatus.COMPLETED);

        OrderDto updatedOrder = new OrderDto();
        updatedOrder.setId(1L);
        updatedOrder.setProduct("MacBook");
        updatedOrder.setAmount(1);
        updatedOrder.setStatus(OrderStatus.CREATED);

        when(orderService.update(eq(1L), any(OrderDto.class))).thenReturn(updatedOrder);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.product").value("MacBook"))
                .andExpect(jsonPath("$.amount").value(1))
                .andExpect(jsonPath("$.status").value(OrderStatus.CREATED.name()));
    }

    @Test
    void delete_shouldDeleteOrder() throws Exception {
        doNothing().when(orderService).delete(1L);

        mockMvc.perform(delete("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}