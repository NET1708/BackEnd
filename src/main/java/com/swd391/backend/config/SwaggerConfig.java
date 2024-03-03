package com.swd391.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${swd391.openapi.dev-url}")
    private String devUrl;

    @Value("${swd391.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {

        OpenAPI openAPI = new OpenAPI();
        //CORS configuration for swagger
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin(devUrl);
        corsConfig.addAllowedOrigin(prodUrl);
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);
        openAPI.addExtension("x-cors", corsConfig);
        //Server configuration
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("vinhpqse160633@fpt.edu.vn");
        contact.setName("Thần Đằng Buôn Mê");
        contact.setUrl("https://www.facebook.com/PhamQuangVinh2002");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Tutorial Management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage tutorials.").termsOfService("https://www.ani-testlab.edu.vn/terms")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
