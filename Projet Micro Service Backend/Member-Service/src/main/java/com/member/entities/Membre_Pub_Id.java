package com.member.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Membre_Pub_Id implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long publication_id;
	private Long auteur_id;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Membre_Pub_Id that = (Membre_Pub_Id) o;

		if (publication_id != null ? !publication_id.equals(that.publication_id) : that.publication_id != null)
			return false;
		return auteur_id != null ? auteur_id.equals(that.auteur_id) : that.auteur_id == null;
	}

	@Override
	public int hashCode() {
		int result = publication_id != null ? publication_id.hashCode() : 0;
		result = 31 * result + (auteur_id != null ? auteur_id.hashCode() : 0);
		return result;
	}
}