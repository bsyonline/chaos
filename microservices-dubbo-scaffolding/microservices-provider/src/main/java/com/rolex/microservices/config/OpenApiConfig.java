package com.rolex.microservices.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("basicScheme", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP).scheme("basic")))
                .info(new Info()
                        .title("API标题")
                        .version("V1.0")
                        .description("API 描述信息")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch("/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {
        return GroupedOpenApi.builder()
                .group("Order")
                .pathsToMatch("/orders/**")
                .build();
    }
}
