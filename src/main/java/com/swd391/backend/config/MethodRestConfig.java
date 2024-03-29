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
    private String prodUrl = "https://ani-testlab.edu.vn";

    private String apiUrl = "https://api.ani-testlab.edu.vn";
    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        //expose all the ids
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream()
                .map(e -> e.getJavaType())
                .toArray(Class[]::new));
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
