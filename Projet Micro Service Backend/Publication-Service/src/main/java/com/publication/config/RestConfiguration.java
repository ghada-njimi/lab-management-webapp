package com.publication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.publication.entities.Publication;

import jakarta.persistence.EntityManager;

@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    // Injection du EntityManager
    public RestConfiguration(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(
            RepositoryRestConfiguration config, 
            CorsRegistry cors) {
        
        // Exposer l'ID pour l'entité Publication
        config.exposeIdsFor(Publication.class);
    }
}
