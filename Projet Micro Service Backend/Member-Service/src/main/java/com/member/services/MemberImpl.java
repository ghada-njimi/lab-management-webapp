package com.member.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.member.beans.PublicationBean;
import com.member.dao.EnseignantChercheurRepository;
import com.member.dao.EtudiantRepository;
import com.member.dao.MemberRepository;
import com.member.dao.MembrePubRepository;
import com.member.entities.EnseignantChercheur;
import com.member.entities.Etudiant;
import com.member.entities.Member;
import com.member.entities.Membre_Pub_Id;
import com.member.entities.Membre_Publication;
import com.member.proxies.PublicationProxyService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MemberImpl implements IMemberService {

	private final MemberRepository memberRepository;
	private final EtudiantRepository etudiantRepository;
	private final EnseignantChercheurRepository enseignantChercheurRepository;
    private final MembrePubRepository membrePubRepository;
    private final PublicationProxyService publicationProxy;  // Injection du Feign Client


	// CRUD Membres
	@Override
	public Member addMember(Member m) {
		return memberRepository.save(m);
	}

	@Override
	public void deleteMember(Long id) {
		memberRepository.deleteById(id);
	}

	@Override
	public Member updateMember(Member m) {
		return memberRepository.saveAndFlush(m);
	}

	@Override
	public Member findMember(Long id) {
		return memberRepository.findById(id).orElse(null);
	}

	@Override
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	// Recherche générale
	@Override
	public Member findByCin(String cin) {
		return memberRepository.findByCin(cin);
	}

	@Override
	public Member findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	@Override
	public List<Member> findByNom(String nom) {
		return memberRepository.findByNom(nom);
	}

	@Override
	public List<Member> findByNomStartingWith(String caractere) {
		return memberRepository.findByNomStartingWith(caractere);
	}

	// Méthodes spécifiques pour Etudiant
	@Override
	public List<Etudiant> findAllEtudiants() {
		return etudiantRepository.findAll();
	}

	@Override
	public Etudiant findEtudiant(Long id) {
		return etudiantRepository.findById(id).orElse(null);
	}

	@Override
	public List<Etudiant> findByDiplome(String diplome) {
		return etudiantRepository.findByDiplome(diplome);
	}

	@Override
	public List<Etudiant> findByDiplomeOrderByDateInscriptionDesc(String diplome) {
		return etudiantRepository.findByDiplomeOrderByDateInscriptionDesc(diplome);
	}

	@Override
	public List<Etudiant> chercherEtudiants(String nom) {
		return etudiantRepository.chercher("%" + nom + "%");
	}

	// Méthodes spécifiques pour EnseignantChercheur
	@Override
	public List<EnseignantChercheur> findAllEnseignants() {
		return enseignantChercheurRepository.findAll();
	}

	@Override
	public EnseignantChercheur findEnseignant(Long id) {
		return enseignantChercheurRepository.findById(id).orElse(null);
	}

	@Override
	public List<EnseignantChercheur> findByGrade(String grade) {
		return enseignantChercheurRepository.findByGrade(grade);
	}

	@Override
	public List<EnseignantChercheur> findByEtablissement(String etablissement) {
		return enseignantChercheurRepository.findByEtablissement(etablissement);
	}

	// Relation Etudiant-Enseignant
	@Override
	public void affecterEtudiantEnseignant(Long idetd, Long idens) {
		Etudiant etd = etudiantRepository.findById(idetd).orElseThrow(() -> new RuntimeException("Etudiant not found"));
		EnseignantChercheur ens = enseignantChercheurRepository.findById(idens)
				.orElseThrow(() -> new RuntimeException("Enseignant not found"));

		etd.setEncadrant(ens);
		etudiantRepository.save(etd);
	}

	@Override
	public List<Etudiant> findEtudiantsByEnseignant(EnseignantChercheur ens) {
		return etudiantRepository.findByEncadrant(ens);
	}

    /**
     * Affecter une publication à un auteur
     */
    @Override
    public void affecterAuteurToPublication(Long idauteur, Long idpub) {
        // Vérifier que le membre existe
        Member mbr = memberRepository.findById(idauteur)
                .orElseThrow(() -> new RuntimeException("Membre not found with id: " + idauteur));
        
        // Vérifier que la publication existe via Feign
        try {
            PublicationBean pub = publicationProxy.findPublicationById(idpub);
            if (pub == null) {
                throw new RuntimeException("Publication not found with id: " + idpub);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error accessing Publication Service: " + e.getMessage());
        }
        
        // Créer l'association
        Membre_Publication membrePub = new Membre_Publication();
        membrePub.setAuteur(mbr);
        membrePub.setId(new Membre_Pub_Id(idpub, idauteur));
        
        // Sauvegarder l'association
        membrePubRepository.save(membrePub);
        
        System.out.println("✓ Publication " + idpub + " affectée à l'auteur " + mbr.getNom() + " " + mbr.getPrenom());
    }
    
    /**
     * Récupérer toutes les publications d'un auteur
     */
    @Override
    public List<PublicationBean> findPublicationParAuteur(Long idauteur) {
        List<PublicationBean> publications = new ArrayList<>();
        
        // Récupérer toutes les associations pour cet auteur
        List<Membre_Publication> associations = membrePubRepository.findpubId(idauteur);
        
        System.out.println("Nombre d'associations trouvées pour l'auteur " + idauteur + " : " + associations.size());
        
        // Pour chaque association, récupérer la publication via Feign
        associations.forEach(association -> {
            try {
                Long publicationId = association.getId().getPublication_id();
                System.out.println("  → Récupération de la publication ID=" + publicationId);
                
                PublicationBean pub = publicationProxy.findPublicationById(publicationId);
                
                if (pub != null) {
                    publications.add(pub);
                    System.out.println("  ✓ Publication récupérée : " + pub.getTitre());
                } else {
                    System.out.println("  ✗ Publication ID=" + publicationId + " non trouvée");
                }
            } catch (Exception e) {
                System.err.println("  ✗ Erreur lors de la récupération de la publication: " + e.getMessage());
            }
        });
        
        return publications;
    }
    
    /**
     * Récupérer un membre avec toutes ses publications
     */
    @Override
    public Member findMemberWithPublications(Long id) {
        Member member = findMember(id);
        
        if (member != null) {
            // Charger les publications du membre
            List<PublicationBean> publications = findPublicationParAuteur(id);
            member.setPubs(publications);
        }
        
        return member;
    }
}