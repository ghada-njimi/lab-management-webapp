package com.member.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membre_Publication {
    
    @EmbeddedId
    private Membre_Pub_Id id;
    
    @ManyToOne
    @MapsId("auteur_id")  // Lie auteur_id de la clé composite à l'entité Member
    private Member auteur;
    
    // On ne stocke pas directement la publication car elle est dans un autre microservice
    // On stocke juste son ID dans la clé composite
}