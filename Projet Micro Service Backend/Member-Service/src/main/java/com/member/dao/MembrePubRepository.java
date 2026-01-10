package com.member.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.member.entities.Membre_Publication;
import com.member.entities.Membre_Pub_Id;

public interface MembrePubRepository extends JpaRepository<Membre_Publication, Membre_Pub_Id> {
    
    /**
     * Récupérer toutes les publications d'un auteur donné
     * @param autId ID de l'auteur
     * @return Liste des associations Membre_Publication
     */
    @Query("SELECT m FROM Membre_Publication m WHERE m.id.auteur_id = :x")
    List<Membre_Publication> findpubId(@Param("x") Long autId);
}