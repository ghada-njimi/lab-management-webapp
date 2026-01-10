package com.publication.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.publication.entities.Publication;

@RepositoryRestResource(path = "publications")
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    
    // Recherche par type
    List<Publication> findByType(@Param("type") String type);
    
    // Recherche par titre contenant
    List<Publication> findByTitreContaining(@Param("titre") String titre);
    
    // Recherche par titre
    List<Publication> findByTitre(@Param("titre") String titre);
    
    // Recherche par date d'apparition
    List<Publication> findByDateApparition(@Param("date") Date date);
    
    // Recherche publications après une date
    List<Publication> findByDateApparitionAfter(@Param("date") Date date);
    
    // Recherche publications avant une date
    List<Publication> findByDateApparitionBefore(@Param("date") Date date);
    
    // Recherche publications entre deux dates
    List<Publication> findByDateApparitionBetween(
        @Param("start") Date startDate, 
        @Param("end") Date endDate
    );
    
    // Recherche par type et ordonné par date
    List<Publication> findByTypeOrderByDateApparitionDesc(@Param("type") String type);
    
    // Requête personnalisée
    @Query("SELECT p FROM Publication p WHERE p.type = :type AND YEAR(p.dateApparition) = :year")
    List<Publication> findByTypeAndYear(
        @Param("type") String type, 
        @Param("year") int year
    );
    
    // Compter par type
    long countByType(@Param("type") String type);
}