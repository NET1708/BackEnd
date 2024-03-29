package com.swd391.backend.config;

import com.swd391.backend.service.JwtService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
    private String apidUrl;

    @Bean
    public SecurityScheme jwtSecurityScheme() {
        return new JwtService().jwtSecuritySchema();
    }

    @Bean
    public OpenAPI myOpenAPI() {

        OpenAPI openAPI = new OpenAPI();
        //CORS configuration for swagger
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin(devUrl);
        corsConfig.addAllowedOrigin(apidUrl);
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setAllowCredentials(true);
        openAPI.addExtension("x-cors", corsConfig);
        //Server configuration
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(apidUrl);
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

        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", jwtSecurityScheme()))
                .info(info).servers(List.of(devServer, prodServer));
    }
}
