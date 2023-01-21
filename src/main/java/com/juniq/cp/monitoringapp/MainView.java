package com.juniq.cp.monitoringapp;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private TextField serverUrl = new TextField("Enter a server URL:");
    private Grid<UrlModel> grid = new Grid<>(UrlModel.class);
    private List<UrlModel> urlList = new ArrayList<>();

    public MainView() {
        add(new H1("URL Monitoring App"));
        add(getForm(), grid);
        add(resetTable());
    }

    private Component resetTable() {
        var layout = new HorizontalLayout();
        var resetButton = new Button("Reset table");
        resetButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        layout.add(resetButton);
        onResetButtonEvent(resetButton);

        return layout;
    }

    private void onResetButtonEvent(Button resetButton) {
        resetButton.addClickListener(click -> {
            urlList.clear();
            refreshGrid();
            serverUrl.clear();
        });
    }

    private Component getForm() {
        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE);
        var addButton = new Button("Add");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.add(serverUrl, addButton);
        onAddButtonEvent(addButton);

        return layout;
    }

    private void onAddButtonEvent(Button addButton) {
        addButton.addClickListener(click -> {
            fillUrlList();
            refreshGrid();
            serverUrl.clear();
        });
    }

    private void fillUrlList() {
        UrlModel url = new UrlModel(serverUrl.getValue());
        String responseResult = UrlValidator.getUrlStatusCode(url);
        url.setStatusCode(responseResult);
        urlList.add(url);
    }

    private void refreshGrid() {
        grid.setItems(urlList);
    }
}
