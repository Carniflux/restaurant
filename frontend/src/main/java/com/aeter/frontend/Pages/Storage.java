package com.aeter.frontend.Pages;

import com.aeter.frontend.Dto.ProductsDto;
import com.aeter.frontend.components.DeliverPartOfProducts;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Route("Storage")
public class Storage extends VerticalLayout {

    private final static String GET_PRODUCTS_URI = "http://localhost:8080/order/getAllFromProducts/";
    private final static String ADD_PRODUCTS_URI = "http://localhost:8080/order/addProducts/";
    private final static String DELETE_PRODUCTS_URI = "http://localhost:8080/order/deleteAllDataFromProducts/";

    private final Grid<ProductsDto> grid = new Grid<>(ProductsDto.class);

    private final RestTemplate restTemplate;

    public Storage(DeliverPartOfProducts deliverPartOfProducts) {
        this.restTemplate = new RestTemplate();

        Dialog dialog = new Dialog();
        Text textForDialog = new Text("Move food to the kitchen?");
        Button confirmButton = new Button("Confirm", event -> {
            movingToTheKitchen();
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        HorizontalLayout dialogActions = new HorizontalLayout(confirmButton, cancelButton);
        dialog.add(textForDialog, dialogActions);

        Button buttonForArrived = new Button("Order arrived");
        Button buttonForDelivering = new Button("Deliver to the kitchen");
        Button buttonForPartDelivering = new Button("Deliver part of product");
        HorizontalLayout actions = new HorizontalLayout(buttonForArrived, buttonForDelivering, buttonForPartDelivering);

        grid.removeColumnByKey("measure");
        grid.addColumn("measure");

        add(new RouterLink("Create order", CreateOrder.class), actions, grid, deliverPartOfProducts);

        buttonForDelivering.addClickListener(e -> dialog.open());
        buttonForArrived.addClickListener(e -> addProductsToStorage());
        buttonForPartDelivering.addClickListener(e -> deliverPartOfProducts.deliverPartOfProducts());

        listProducts();
    }

    private void listProducts() {
        ResponseEntity<List<ProductsDto>> rateResponse =
                restTemplate.exchange(GET_PRODUCTS_URI,
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<ProductsDto> dto = rateResponse.getBody();
        grid.setItems(dto);
    }

    private void addProductsToStorage() {
        restTemplate.exchange(ADD_PRODUCTS_URI, HttpMethod.POST, null, Void.class);
        listProducts();
    }

    private void movingToTheKitchen(){
        restTemplate.exchange(DELETE_PRODUCTS_URI, HttpMethod.DELETE, null, Void.class);
        listProducts();
    }
}
