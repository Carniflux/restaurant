package com.aeter.backend.services;

import com.aeter.backend.Dto.ProductsDto;
import com.aeter.backend.entity.Order;
import com.aeter.backend.entity.Products;
import com.aeter.backend.repo.OrderRepo;
import com.aeter.backend.repo.ProductsRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductsService {

    private final OrderRepo orderRepo;
    private final ProductsRepo productsRepo;

    @Transactional
    public void movingToProducts(){
        List<Order> listOder = orderRepo.findAll();
        for(Order o : listOder){
            Products p = new Products();
            p.setQuantity(o.getQuantity());
            p.setName(o.getName());
            p.setMeasure(o.getMeasure());
            productsRepo.save(p);
        }
        orderRepo.deleteAll();
    }

    @Transactional
    public List<ProductsDto> getAllProducts(){
        return productsRepo.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ProductsDto convertToDto(final Products products) {
        ProductsDto productsDto = new ProductsDto();
        productsDto.setName(products.getName());
        productsDto.setQuantity(products.getQuantity());
        productsDto.setMeasure(products.getMeasure());
        return productsDto;
    }

    @Transactional
    public void updateProductsQuantity(String name, Integer updateQuantity){
        productsRepo.update(updateQuantity ,name);
    }

    @Transactional
    public void deleteAll() {
        productsRepo.deleteAll();
    }

    @Transactional
    public void deleteByName(String name){
        productsRepo.deleteByName(name);
    }
}
