package com.juniq.cp.monitoringapp;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.*;

@Route("")
public class MainView extends VerticalLayout {

    private TextField serverUrl = new TextField("Enter a server URL:");
    private IntegerField timeDelay = new IntegerField("Enter time delay (in seconds)");
    private Grid<UrlModel> grid = new Grid<>(UrlModel.class);
    private List<UrlModel> urlList = new ArrayList<>();

    public MainView() {
        add(new H1("URL Monitoring App"));
        add(new H4("Enter a valid URL and a time delay for processing it."));
        grid.setColumns("serverName", "statusCode");
        add(getForm(), grid);
    }

    private Component getForm() {
        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE);
        Button addButton = getAddButton();
        timeDelay.setMin(0);
        timeDelay.setMax(90);
        timeDelay.setValue(0);
        timeDelay.setStepButtonsVisible(true);
        timeDelay.setWidth("212px");

        layout.add(serverUrl, timeDelay, addButton);

        return layout;
    }

    private Button getAddButton() {
        var addButton = new Button("Check URL");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        onAddButtonEvent(addButton);

        return addButton;
    }

    private void onAddButtonEvent(Button addButton) {
        addButton.addClickListener(click -> {
            if (timeDelay.getValue() < 0) {
                Notification.show("Time value has to be higher than 0!");
                return;
            }

            fillUrlList();
            refreshGrid();
            serverUrl.clear();
        });
    }

    private void fillUrlList() {
        UrlModel urlModel = null;

        try {
            urlModel = new UrlModel(serverUrl.getValue());
            runValidationTask(urlModel);
        } catch (URISyntaxException | MalformedURLException e) {
            urlModel = new UrlModel();
            urlModel.setServerName("Invalid URL entered");
            urlModel.setStatusCode(UrlStatus.URL_MALFORMED);
        } finally {
            urlList.add(urlModel);
        }
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
