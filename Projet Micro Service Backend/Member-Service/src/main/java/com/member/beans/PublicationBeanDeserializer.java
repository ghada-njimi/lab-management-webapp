package com.member.beans;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class PublicationBeanDeserializer extends JsonDeserializer<PublicationBean> {
    
    @Override
    public PublicationBean deserialize(JsonParser jp, DeserializationContext ctxt) 
            throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        PublicationBean pub = new PublicationBean();
        
        // Mapper les champs standards
        pub.setTitre(node.get("titre").asText());
        pub.setType(node.get("type").asText());
        pub.setLien(node.get("lien").asText());
        pub.setSourcePdf(node.get("sourcePdf").asText());
        
        // Extraire l'ID depuis le lien self si présent
        if (node.has("_links") && node.get("_links").has("self")) {
            String href = node.get("_links").get("self").get("href").asText();
            String[] parts = href.split("/");
            pub.setId(Long.parseLong(parts[parts.length - 1]));
        }
        
        return pub;
    }
}