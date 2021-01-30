package com.aeter.backend.services;

import com.aeter.backend.Dto.OrderDto;
import com.aeter.backend.entity.Order;
import com.aeter.backend.repo.OrderRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;

    @Transactional
    public void insertOrder(final OrderDto order){
        final Order createOrder = Order.builder()
                .name(order.getName())
                .quantity(order.getQuantity())
                .build();
      orderRepo.save(createOrder);
    }

    public List<OrderDto> getAllOrder(){
        return ((List<Order>) orderRepo.findAll())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderDto convertToDto(final Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setName(order.getName());
        orderDto.setQuantity(order.getQuantity());
        return orderDto;
    }
}
