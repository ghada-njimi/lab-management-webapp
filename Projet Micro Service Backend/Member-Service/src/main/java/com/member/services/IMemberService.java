package com.member.services;

import java.util.List;

import com.member.beans.PublicationBean;
import com.member.entities.EnseignantChercheur;
import com.member.entities.Etudiant;
import com.member.entities.Member;

public interface IMemberService {
    // CRUD sur les membres
    Member addMember(Member m);
    void deleteMember(Long id);
    Member updateMember(Member p);
    Member findMember(Long id);
    List<Member> findAll();
    
    // Recherche générale
    Member findByCin(String cin);
    Member findByEmail(String email);
    List<Member> findByNom(String nom);
    List<Member> findByNomStartingWith(String caractere);
    
    // Méthodes spécifiques pour Etudiant
    List<Etudiant> findAllEtudiants();
    Etudiant findEtudiant(Long id);
    List<Etudiant> findByDiplome(String diplome);
    List<Etudiant> findByDiplomeOrderByDateInscriptionDesc(String diplome);
    List<Etudiant> chercherEtudiants(String nom);
    
    // Méthodes spécifiques pour EnseignantChercheur
    List<EnseignantChercheur> findAllEnseignants();
    EnseignantChercheur findEnseignant(Long id);
    List<EnseignantChercheur> findByGrade(String grade);
    List<EnseignantChercheur> findByEtablissement(String etablissement);
    
    // Relation Etudiant-Enseignant
    void affecterEtudiantEnseignant(Long idetd, Long idens);
    List<Etudiant> findEtudiantsByEnseignant(EnseignantChercheur ens);
    /**
     * Affecter une publication à un auteur (membre)
     * @param idauteur ID du membre auteur
     * @param idpub ID de la publication
     */
    void affecterAuteurToPublication(Long idauteur, Long idpub);
    
    /**
     * Récupérer toutes les publications d'un auteur
     * @param idauteur ID de l'auteur
     * @return Liste des publications de cet auteur
     */
    List<PublicationBean> findPublicationParAuteur(Long idauteur);
    
    /**
     * Récupérer un membre avec toutes ses publications
     * @param id ID du membre
     * @return Membre avec ses publications chargées
     */
    Member findMemberWithPublications(Long id);
}