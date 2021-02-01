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
    public void insertOrder(final OrderDto orderDto){
        final Order createOrder = Order.builder()
                .name(orderDto.getName())
                .quantity(orderDto.getQuantity())
                .measure(orderDto.getMeasure())
                .build();
      orderRepo.save(createOrder);
    }

    @Transactional
    public List<OrderDto> getAllOrder(){
        return orderRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public OrderDto convertToDto(final Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setName(order.getName());
        orderDto.setQuantity(order.getQuantity());
        orderDto.setMeasure(order.getMeasure());
        return orderDto;
    }
}
