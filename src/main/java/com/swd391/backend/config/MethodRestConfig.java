package com.swd391.backend.config;

import com.swd391.backend.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Locale;

@Component
public class MethodRestConfig implements RepositoryRestConfigurer {
    private String url = "http://localhost:3000";
    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        //expose all the ids
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
                .map(e -> e.getJavaType())
                .toArray(Class[]::new));
        //CORS configuration
        cors.addMapping("/**")
                .allowedOrigins(url)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

        // Chặn các methods
        HttpMethod[] chanCacPhuongThuc ={
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.PATCH,
                HttpMethod.DELETE,
        };
//        disableHttpMethods(.class, config, chanCacPhuongThuc);

        // Chặn các method DELETE
        HttpMethod[] phuongThucDelete = {
                HttpMethod.DELETE
        };
        disableHttpMethods(User.class, config,phuongThucDelete );
    }

    private void disableHttpMethods(Class c,
                                    RepositoryRestConfiguration config,
                                    HttpMethod[] methods){
        config.getExposureConfiguration()
                .forDomainType(c)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(methods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(methods));
    }
}
