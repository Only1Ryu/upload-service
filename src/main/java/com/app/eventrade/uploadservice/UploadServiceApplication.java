package com.app.eventrade.uploadservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories
public class UploadServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadServiceApplication.class, args);
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.OAS_30)
				.apiInfo(new ApiInfoBuilder().title("Upload Service API")
						.description("EvenTrade - Upload Service API Version 1").version("0.0.1-SNAPSHOT")
						.license("EvenTrade").licenseUrl("https://eventrade.com")
						.contact(new Contact("Rakesh Sahani", "", "rakeshsahani@live.in")).build())
				.tags(new Tag("Upload Service API", "Endpoints for Upload operations")).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).build();
	}
}