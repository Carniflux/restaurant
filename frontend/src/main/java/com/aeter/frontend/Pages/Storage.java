package com.aeter.frontend.Pages;

import com.aeter.frontend.Dto.ProductsDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Route("Storage")
public class Storage extends VerticalLayout {

    private String getProductsUri = "http://localhost:8080/order/getAllFromProducts/";
    private String addProductsUri = "http://localhost:8080/order/addProducts/";
    private String deleteProductsUri = "http://localhost:8080/order/deleteAllDataFromProducts/";

    private Grid<ProductsDto> grid = new Grid<>(ProductsDto.class);

    private final RestTemplate restTemplate;

    private final Button buttonForArrived = new Button("Order arrived");
    private final Button buttonForDelivering = new Button("Deliver to the kitchen");

    private HorizontalLayout actions = new HorizontalLayout(buttonForArrived, buttonForDelivering);

    @Autowired
    public Storage() {
        this.restTemplate = new RestTemplate();

        add(new RouterLink("Create order", CreateOrder.class), actions, grid );
        buttonForDelivering.addClickListener(e -> movingToTheKitchen());
        buttonForArrived.addClickListener(e -> addProductsToStorage());
        listProducts();
    }

    private void listProducts() {
        ResponseEntity<List<ProductsDto>> rateResponse =
                restTemplate.exchange(getProductsUri,
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<ProductsDto> dto = rateResponse.getBody();
        grid.setItems(dto);
    }

    private void addProductsToStorage() {
        restTemplate.exchange(addProductsUri, HttpMethod.POST, null, Void.class);
        listProducts();
    }

    private void movingToTheKitchen(){
        restTemplate.exchange(deleteProductsUri, HttpMethod.DELETE, null, Void.class);
    }
}
