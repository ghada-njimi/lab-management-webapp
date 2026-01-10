package com.member.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Classe pour gérer la réponse HAL de Spring Data REST
 */
@Data
public class HalPublicationResponse {
    
    @JsonProperty("_embedded")
    private EmbeddedPublications embedded;
    
    @Data
    public static class EmbeddedPublications {
        private List<PublicationBean> publications;
    }
    
    public List<PublicationBean> getPublications() {
        return embedded != null ? embedded.getPublications() : List.of();
    }
}