package com.aeter.frontend.components;

import com.aeter.frontend.Dto.OrderDto;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;




@SpringComponent
@UIScope
public class EditOrder extends VerticalLayout implements KeyNotifier {

    private final RestTemplate restTemplate;

    private final String orderUri = "http://localhost:8080/order/addOrder/";

    private TextField name = new TextField("Name");
    private IntegerField quantity = new IntegerField("Quantity");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");

    private HorizontalLayout actions = new HorizontalLayout(save, cancel);

    Binder<OrderDto> binder = new Binder<>(OrderDto.class);
    private OrderDto orderDto = new OrderDto();

    @Autowired
    public EditOrder(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        add(name, quantity, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        cancel.addClickListener(e -> UI.getCurrent().getPage().reload());

        setVisible(false);
    }

    private void save() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OrderDto> httpEntity = new HttpEntity(orderDto, httpHeaders);

        restTemplate.postForEntity(orderUri, httpEntity, Void.class);
        Notification.show(orderDto.getName() + " " + "added to order list");

    }

    public void editOrder(OrderDto newOrder) {
        binder.setBean(orderDto);
        setVisible(true);
        name.focus();
    }

}
