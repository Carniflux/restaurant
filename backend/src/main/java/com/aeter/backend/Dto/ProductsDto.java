package com.aeter.backend.Dto;

import lombok.Getter;

@Getter
public class ProductsDto {

    private final String name;

    private final Integer quantity;

    private final String measure;

    public ProductsDto(final String name,final Integer quantity,final String measure) {
        this.name = name;
        this.quantity = quantity;
        this.measure = measure;
    }
}
