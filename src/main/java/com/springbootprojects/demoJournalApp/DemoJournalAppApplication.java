package com.springbootprojects.demoJournalApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

@SpringBootApplication
public class DemoJournalAppApplication implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	public static void main(String[] args) {
		SpringApplication.run(DemoJournalAppApplication.class, args);
	}

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		factory.setPort(9090);
	}
}
