package com.member.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.member.entities.EnseignantChercheur;
import com.member.entities.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

	@Query("select e from Etudiant e where e.nom like :x")
	public List<Etudiant> chercher(@Param("x") String mc);

	List<Etudiant> findByDiplome(String diplome);

	List<Etudiant> findByDiplomeOrderByDateInscriptionDesc(String diplome);

	public List<Etudiant> findByEncadrant(EnseignantChercheur ens);
}
