package com.juniq.cp.monitoringapp;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Route("")
public class MainView extends VerticalLayout {

    private TextField serverUrl = new TextField("Enter a server URL:");
    private IntegerField timeDelay = new IntegerField("Time delay in seconds");
    private Grid<UrlModel> grid = new Grid<>(UrlModel.class);
    private List<UrlModel> urlList = new ArrayList<>();

    public MainView() {
        add(new H1("URL Monitoring App"));
        grid.setColumns("serverName", "statusCode");
        add(getForm(), grid);
    }

    private Component getForm() {
        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE);
        Button addButton = getAddButton();

        timeDelay.setMin(0);
        timeDelay.setMax(60);
        timeDelay.setValue(2);
        timeDelay.setStepButtonsVisible(true);
        timeDelay.setWidthFull();

        layout.add(serverUrl, timeDelay, addButton);

        return layout;
    }

    private Button getAddButton() {
        var addButton = new Button("Add");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        onAddButtonEvent(addButton);

        return addButton;
    }

    private void onAddButtonEvent(Button addButton) {
        addButton.addClickListener(click -> {
            fillUrlList();
            refreshGrid();
            serverUrl.clear();
        });
    }

    private void fillUrlList() {
        UrlModel urlModel = new UrlModel(serverUrl.getValue());

        try {
            URI uri = new URI(serverUrl.getValue());
            urlModel.setServerName(!Objects.isNull(uri.getHost()) ? uri.getHost() : "Invalid");
            urlModel.setStatusCode(UrlStatus.FETCHING);
            runValidationTask(urlModel);
        } catch (URISyntaxException e) {
            urlModel.setServerName("Invalid");
        }

        urlList.add(urlModel);
    }

    private void runValidationTask(UrlModel urlModel) {
        Timer timer = new Timer("Timer");
        long delay = timeDelay.getValue() * 1000;
        timer.schedule(createValidationTask(urlModel), delay);
    }

    private TimerTask createValidationTask(UrlModel urlModel) {
        return new TimerTask() {
            public void run() {
                getUI().ifPresent(ui -> ui.access(() -> {
                    urlModel.setStatusCode(UrlValidator.getUrlStatusCode(urlModel));
                    refreshGrid();
                }));
            }
        };
    }

    private void refreshGrid() {
        grid.setItems(urlList);
    }
}
