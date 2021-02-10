package com.aeter.backend.Dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class OrderDto {

    private final String name;

    private final Integer quantity;

    private final String measure;

    public OrderDto(final String name,
                    final Integer quantity,
                    final String measure) {
        this.name = name;
        this.quantity = quantity;
        this.measure = measure;
    }


}
