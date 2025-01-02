package com.assignment.vehiclemanagementsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/*
 * @author: TanTD1
 * @since: 8/7/2024 1:13 PM
 * @description:  OpenAipConfig class
 * */
import org.springdoc.core.models.GroupedOpenApi;


@Configuration
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi publicApi(@Value("${openapi.service.api-docs}") String apiDocs) {
        return GroupedOpenApi.builder()
                .group(apiDocs) // /v3/api-docs/api-service
                .packagesToScan("com.assignment.vehiclemanagementsystem.controller")
                .build();
    }

    @Bean
    public OpenAPI openAPI(
            @Value("${openapi.service.title}") String title,
            @Value("${openapi.service.version}") String version,
            @Value("${openapi.service.server}") String serverUrl) {
        return new OpenAPI()
                .servers(List.of(new Server().url(serverUrl)))
                .info(new Info().title(title)
                        .description("Vehicle Management System API")
                        .version(version)
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                        .servers(List.of(new Server().url(serverUrl).description("Main server")));
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("com.assignment.vehiclemanagementsystem.controller.*")
                .build();
    }
}
