package com.outil.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.outil.entities.Outil;

@RepositoryRestResource(path = "outils")
public interface OutilRepository extends JpaRepository<Outil, Long> {

	// Recherche par source
	List<Outil> findBySource(@Param("source") String source);

	// Recherche par source contenant
	List<Outil> findBySourceContaining(@Param("source") String source);

	// Recherche par date
	List<Outil> findByDate(@Param("date") Date date);

	// Recherche après une date
	List<Outil> findByDateAfter(@Param("date") Date date);

	// Recherche avant une date
	List<Outil> findByDateBefore(@Param("date") Date date);

	// Recherche entre deux dates
	List<Outil> findByDateBetween(@Param("start") Date startDate, @Param("end") Date endDate);

	// Ordonné par date décroissante
	List<Outil> findAllByOrderByDateDesc();

	// Ordonné par source
	List<Outil> findAllByOrderBySourceAsc();

	// Requête personnalisée - Outils d'une année spécifique
	@Query("SELECT o FROM Outil o WHERE YEAR(o.date) = :year")
	List<Outil> findByYear(@Param("year") int year);

	// Compter par source
	long countBySource(@Param("source") String source);
}