package com.member.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.member.beans.HalPublicationResponse;
import com.member.beans.PublicationBean;

/**
 * Client Feign pour communiquer avec Publication-Service
 */
@FeignClient(name = "PUBLICATION-SERVICE")
public interface PublicationProxyService {
    
    /**
     * Récupérer une publication par son ID
     * IMPORTANT: Spring Data REST retourne directement l'objet sans wrapper
     */
    @GetMapping("/api/publications/{id}")
    PublicationBean findPublicationById(@PathVariable("id") Long id);
    
    /**
     * Récupérer toutes les publications
     * Note: Spring Data REST retourne un format HAL, 
     * donc cette méthode ne fonctionnera pas directement
     */
    @GetMapping("/api/publications")
    HalPublicationResponse findAllPublications();
    
    /**
     * Rechercher par type
     */
    @GetMapping("/api/publications/search/findByType")
    HalPublicationResponse findByType(@RequestParam("type") String type);
}