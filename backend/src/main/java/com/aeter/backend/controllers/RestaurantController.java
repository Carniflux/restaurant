package com.aeter.backend.controllers;

import com.aeter.backend.Dto.OrderDto;
import com.aeter.backend.Dto.ProductsDto;
import com.aeter.backend.entity.Order;
import com.aeter.backend.services.OrderService;
import com.aeter.backend.services.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Api(value = "OrderControllerAPI", tags = "order")
@AllArgsConstructor
public class RestaurantController {

    private final OrderService orderService;
    private final ProductsService productsService;

    @PostMapping(value = "addOrder")
    @ApiOperation(value = "Insert products for order", tags = "order")
    @ApiResponses(value ={@ApiResponse(code = 201, message = "OK", response = Order.class)})
    public void addOrder(@RequestBody OrderDto orderDto) {
        orderService.insertOrder(orderDto);
    }

    @GetMapping(value = "getAllFromOrder")
    @ApiOperation(value = "Searching all products in order")
    public List<OrderDto> getAllFromOrder() {
        return orderService.getAllOrder();
    }

    @PostMapping(value = "addProducts")
    @ApiOperation(value = "Add products")
    public void addProducts() {
        productsService.movingToProducts();
    }

    @GetMapping(value = "getAllFromProducts")
    @ApiOperation(value = "Get all data from products")
    public List<ProductsDto> getAllFromProducts() {
        return productsService.getAllProducts();
    }

    @PostMapping(value = "updateQuantityOfProduct")
    @ApiOperation(value = "Update quantity of product")
    public void updateQuantity(@RequestBody ProductsDto productsDto) {
        productsService.updateProductsQuantity(productsDto.getName().trim(), productsDto.getQuantity());
    }

    @DeleteMapping(value = "deleteAllDataFromProducts")
    @ApiOperation(value = "Moving to kitchen")
    public void deleteAllData() {
        productsService.deleteAll();
    }

    @DeleteMapping(value = "deleteByName")
    @ApiOperation(value = "Delete by name")
    public void deleteByName(@RequestBody ProductsDto productsDto) {
        productsService.deleteByName(productsDto.getName().trim());
    }
}
