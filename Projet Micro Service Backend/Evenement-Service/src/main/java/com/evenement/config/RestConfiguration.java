package com.evenement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.evenement.entities.Evenement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;

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
        
        // SOLUTION 1 : Exposer l'ID pour l'entité Evenement
        config.exposeIdsFor(Evenement.class);
        
        // SOLUTION 2 (ALTERNATIVE) : Exposer les IDs pour TOUTES les entités JPA
        // Décommentez si vous voulez exposer les IDs de toutes vos entités
        /*
        config.exposeIdsFor(
            entityManager.getMetamodel()
                .getEntities()
                .stream()
                .map(Type::getJavaType)
                .toArray(Class[]::new)
        );
        */
    }
}