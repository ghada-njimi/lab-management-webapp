package com.member.entities;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.member.beans.PublicationBean;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("etd")
@JsonTypeName("etd")
@Setter
@Getter
@NoArgsConstructor
public class Etudiant extends Member {
    
    private Date dateInscription;
    private String diplome;
    
    @ManyToOne
    private EnseignantChercheur encadrant;
    
    // Constructeur complet
    public Etudiant(Long id, String cin, String nom, String prenom, Date date, 
                    String photo, String cv, String email, String password,
                    Collection<PublicationBean> pubs,
                    Date dateInscription, String diplome, EnseignantChercheur encadrant) {
        super(id, cin, nom, prenom, date, photo, cv, email, password, pubs);
        this.dateInscription = dateInscription;
        this.diplome = diplome;
        this.encadrant = encadrant;
    }
    
    // Builder personnalisé
    public static EtudiantBuilder builder() {
        return new EtudiantBuilder();
    }
    
    public static class EtudiantBuilder {
        private Long id;
        private String cin;
        private String nom;
        private String prenom;
        private Date date;
        private String photo;
        private String cv;
        private String email;
        private String password;
        private Collection<PublicationBean> pubs;
        private Date dateInscription;
        private String diplome;
        private EnseignantChercheur encadrant;
        
        public EtudiantBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public EtudiantBuilder cin(String cin) {
            this.cin = cin;
            return this;
        }
        
        public EtudiantBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }
        
        public EtudiantBuilder prenom(String prenom) {
            this.prenom = prenom;
            return this;
        }
        
        public EtudiantBuilder date(Date date) {
            this.date = date;
            return this;
        }
        
        public EtudiantBuilder photo(String photo) {
            this.photo = photo;
            return this;
        }
        
        public EtudiantBuilder cv(String cv) {
            this.cv = cv;
            return this;
        }
        
        public EtudiantBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public EtudiantBuilder password(String password) {
            this.password = password;
            return this;
        }
        
        public EtudiantBuilder pubs(Collection<PublicationBean> pubs) {
            this.pubs = pubs;
            return this;
        }
        
        public EtudiantBuilder dateInscription(Date dateInscription) {
            this.dateInscription = dateInscription;
            return this;
        }
        
        public EtudiantBuilder diplome(String diplome) {
            this.diplome = diplome;
            return this;
        }
        
        public EtudiantBuilder encadrant(EnseignantChercheur encadrant) {
            this.encadrant = encadrant;
            return this;
        }
        
        public Etudiant build() {
            return new Etudiant(id, cin, nom, prenom, date, photo, cv, email, password,
                               pubs, dateInscription, diplome, encadrant);
        }
    }
    
    @Override
    public String toString() {
        return "Etudiant{" +
                "id=" + getId() +
                ", cin='" + getCin() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", date=" + getDate() +
                ", email='" + getEmail() + '\'' +
                ", diplome='" + diplome + '\'' +
                ", dateInscription=" + dateInscription +
                '}';
    }
}