package com.member.entities;

import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.member.beans.PublicationBean;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("ens")
@JsonTypeName("ens")
@Setter
@Getter
@NoArgsConstructor
public class EnseignantChercheur extends Member {

	private String grade;
	private String etablissement;

	// Constructeur complet
	public EnseignantChercheur(Long id, String cin, String nom, String prenom, Date date, String photo, String cv,
			String email, String password, Collection<PublicationBean> pubs, String grade, String etablissement) {
		super(id, cin, nom, prenom, date, photo, cv, email, password, pubs);
		this.grade = grade;
		this.etablissement = etablissement;
	}

	// Builder personnalisé
	public static EnseignantChercheurBuilder builder() {
		return new EnseignantChercheurBuilder();
	}

	public static class EnseignantChercheurBuilder {
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
		private String grade;
		private String etablissement;

		public EnseignantChercheurBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public EnseignantChercheurBuilder cin(String cin) {
			this.cin = cin;
			return this;
		}

		public EnseignantChercheurBuilder nom(String nom) {
			this.nom = nom;
			return this;
		}

		public EnseignantChercheurBuilder prenom(String prenom) {
			this.prenom = prenom;
			return this;
		}

		public EnseignantChercheurBuilder date(Date date) {
			this.date = date;
			return this;
		}

		public EnseignantChercheurBuilder photo(String photo) {
			this.photo = photo;
			return this;
		}

		public EnseignantChercheurBuilder cv(String cv) {
			this.cv = cv;
			return this;
		}

		public EnseignantChercheurBuilder email(String email) {
			this.email = email;
			return this;
		}

		public EnseignantChercheurBuilder password(String password) {
			this.password = password;
			return this;
		}

		public EnseignantChercheurBuilder pubs(Collection<PublicationBean> pubs) {
			this.pubs = pubs;
			return this;
		}

		public EnseignantChercheurBuilder grade(String grade) {
			this.grade = grade;
			return this;
		}

		public EnseignantChercheurBuilder etablissement(String etablissement) {
			this.etablissement = etablissement;
			return this;
		}

		public EnseignantChercheur build() {
			return new EnseignantChercheur(id, cin, nom, prenom, date, photo, cv, email, password, pubs, grade,
					etablissement);
		}
	}

	@Override
	public String toString() {
		return "EnseignantChercheur{" + "id=" + getId() + ", cin='" + getCin() + '\'' + ", nom='" + getNom() + '\''
				+ ", prenom='" + getPrenom() + '\'' + ", date=" + getDate() + ", email='" + getEmail() + '\''
				+ ", grade='" + grade + '\'' + ", etablissement='" + etablissement + '\'' + '}';
	}
}