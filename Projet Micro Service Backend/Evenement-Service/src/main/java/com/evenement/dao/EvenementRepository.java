package com.evenement.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.evenement.entities.Evenement;

@RepositoryRestResource(path = "evenements")
public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    
    // Recherche par titre
    List<Evenement> findByTitre(@Param("titre") String titre);
    
    // Recherche par titre contenant
    List<Evenement> findByTitreContaining(@Param("titre") String titre);
    
    // Recherche par lieu
    List<Evenement> findByLieu(@Param("lieu") String lieu);
    
    // Recherche par lieu contenant
    List<Evenement> findByLieuContaining(@Param("lieu") String lieu);
    
    // Recherche par date
    List<Evenement> findByDate(@Param("date") Date date);
    
    // Événements après une date
    List<Evenement> findByDateAfter(@Param("date") Date date);
    
    // Événements avant une date
    List<Evenement> findByDateBefore(@Param("date") Date date);
    
    // Événements entre deux dates
    List<Evenement> findByDateBetween(@Param("start") Date startDate, @Param("end") Date endDate);
    
    // Événements ordonnés par date décroissante
    List<Evenement> findAllByOrderByDateDesc();
    
    // Événements ordonnés par titre
    List<Evenement> findAllByOrderByTitreAsc();
    
    // Recherche par lieu et date
    List<Evenement> findByLieuAndDate(@Param("lieu") String lieu, @Param("date") Date date);
    
    // Événements d'un lieu ordonnés par date
    List<Evenement> findByLieuOrderByDateDesc(@Param("lieu") String lieu);
    
    // Requête personnalisée - Événements d'une année
    @Query("SELECT e FROM Evenement e WHERE YEAR(e.date) = :year")
    List<Evenement> findByYear(@Param("year") int year);
    
    // Requête personnalisée - Événements d'un mois et année
    @Query("SELECT e FROM Evenement e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")
    List<Evenement> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
    
    // Compter par lieu
    long countByLieu(@Param("lieu") String lieu);
    
    // Événements à venir
    @Query("SELECT e FROM Evenement e WHERE e.date >= CURRENT_DATE ORDER BY e.date ASC")
    List<Evenement> findUpcomingEvents();
    
    // Événements passés
    @Query("SELECT e FROM Evenement e WHERE e.date < CURRENT_DATE ORDER BY e.date DESC")
    List<Evenement> findPastEvents();
}