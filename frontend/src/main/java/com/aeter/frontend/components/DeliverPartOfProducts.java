package com.aeter.frontend.components;

import com.aeter.frontend.Dto.ProductsDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@UIScope
@SpringComponent
public class DeliverPartOfProducts extends VerticalLayout{
    private final RestTemplate restTemplate;

    private final ProductsDto productsDto = new ProductsDto();

    private final static String UPDATE_URI = "http://localhost:8080/order/updateQuantityOfProduct/";
    private final static String DELETE_URI = "http://localhost:8080/order/deleteByName/";

    private final TextField name = new TextField("Name");
    private final IntegerField quantity = new IntegerField("Quantity");

    Binder<ProductsDto> binder = new Binder<>(ProductsDto.class);

    @Autowired
    public DeliverPartOfProducts(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        Button deliverAllProduct = new Button("Deliver all product");
        Button deliverPart = new Button("Deliver part");
        Button cancel = new Button("Cancel");

        HorizontalLayout actions = new HorizontalLayout(deliverAllProduct, deliverPart, cancel);

        add(name, quantity, actions);

        quantity.setMinWidth("192px");

        binder.bindInstanceFields(this);

        setSpacing(true);

        deliverAllProduct.addClickListener(e -> deleteByName());
        deliverPart.addClickListener(e -> updateQuantityProducts());
        cancel.addClickListener(e -> UI.getCurrent().getPage().reload());

        setVisible(false);
    }

    private void deleteByName(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductsDto> httpEntity = new HttpEntity<>(productsDto, httpHeaders);
        restTemplate.exchange(DELETE_URI, HttpMethod.DELETE, httpEntity, String.class);

        name.clear();
        quantity.clear();
    }

    private void updateQuantityProducts(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductsDto> httpEntity = new HttpEntity<>(productsDto, httpHeaders);

        restTemplate.exchange(UPDATE_URI, HttpMethod.POST, httpEntity, Void.class);

        name.clear();
        quantity.clear();
    }

    public void deliverPartOfProducts(){
        binder.setBean(productsDto);
        setVisible(true);
        name.focus();
    }
}
