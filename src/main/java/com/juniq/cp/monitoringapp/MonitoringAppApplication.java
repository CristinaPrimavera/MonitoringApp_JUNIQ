package com.juniq.cp.monitoringapp;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.shared.communication.PushMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication()
@Push(PushMode.AUTOMATIC)
public class MonitoringAppApplication extends SpringBootServletInitializer implements AppShellConfigurator {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MonitoringAppApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MonitoringAppApplication.class, args);
	}

}
