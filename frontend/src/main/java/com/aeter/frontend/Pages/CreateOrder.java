package com.aeter.frontend.Pages;

import com.aeter.frontend.Dto.OrderDto;
import com.aeter.frontend.components.EditOrder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Route("CreateOrder")
public class CreateOrder extends VerticalLayout {

    private final Grid<OrderDto> grid = new Grid<>(OrderDto.class);

    private final RestTemplate restTemplate;

    private final static String GET_ORDER_URI = "http://localhost:8080/order/getAllFromOrder/";

    public CreateOrder(EditOrder edit) {
        this.restTemplate = new RestTemplate();

        Button newButton = new Button("Add order");

        grid.removeColumnByKey("measure");
        grid.addColumn("measure");

        add(new RouterLink("Storage", Storage.class), newButton, grid, edit);

        listProductsForOrder();

        newButton.addClickListener(e -> edit.editOrder());
    }

    private void listProductsForOrder() {
        ResponseEntity<List<OrderDto>> rateResponse = restTemplate.exchange(GET_ORDER_URI,
                        HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });
        List<OrderDto> dto= rateResponse.getBody();
        grid.setItems(dto);
    }
}
