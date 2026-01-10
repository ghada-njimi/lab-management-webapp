package com.member.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.member.beans.PublicationBean;
import com.member.entities.EnseignantChercheur;
import com.member.entities.Etudiant;
import com.member.entities.Member;
import com.member.services.IMemberService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
//@CrossOrigin(origins = "*") // Pour permettre les appels depuis un frontend
public class MemberController {

	private final IMemberService memberService;

	@PostMapping
	public ResponseEntity<Member> addMember(@RequestBody Member m) {
	    try {
	        System.out.println("=== RÉCEPTION D'UN NOUVEAU MEMBRE ===");
	        System.out.println("Type de classe reçu: " + m.getClass().getName());
	        System.out.println("Nom: " + m.getNom());
	        System.out.println("Prénom: " + m.getPrenom());
	        
	        if (m instanceof Etudiant) {
	            System.out.println("C'est un ÉTUDIANT");
	            System.out.println("Diplôme: " + ((Etudiant) m).getDiplome());
	        } else if (m instanceof EnseignantChercheur) {
	            System.out.println("C'est un ENSEIGNANT");
	            System.out.println("Grade: " + ((EnseignantChercheur) m).getGrade());
	        }
	        
	        Member savedMember = memberService.addMember(m);
	        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
	    } catch (Exception e) {
	        System.err.println("ERREUR lors de la création:");
	        e.printStackTrace();
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
		try {
			memberService.deleteMember(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member m) {
		try {
			m.setId(id);
			Member updatedMember = memberService.updateMember(m);
			return new ResponseEntity<>(updatedMember, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Member> findMember(@PathVariable Long id) {
		try {
			Member member = memberService.findMember(id);
			if (member != null) {
				return new ResponseEntity<>(member, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<Member>> findAll() {
		try {
			List<Member> members = memberService.findAll();
			return new ResponseEntity<>(members, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Endpoints spécifiques pour Etudiant
	@GetMapping("/etudiants")
	public ResponseEntity<List<Etudiant>> getAllEtudiants() {
		try {
			List<Etudiant> etudiants = memberService.findAllEtudiants();
			return new ResponseEntity<>(etudiants, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/etudiants/{id}")
	public ResponseEntity<Etudiant> findEtudiant(@PathVariable Long id) {
		try {
			Etudiant etudiant = memberService.findEtudiant(id);
			if (etudiant != null) {
				return new ResponseEntity<>(etudiant, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/etudiants/diplome/{diplome}")
	public ResponseEntity<List<Etudiant>> findByDiplome(@PathVariable String diplome) {
		try {
			List<Etudiant> etudiants = memberService.findByDiplome(diplome);
			return new ResponseEntity<>(etudiants, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/etudiants/diplome/{diplome}/ordered")
	public ResponseEntity<List<Etudiant>> findByDiplomeOrdered(@PathVariable String diplome) {
		try {
			List<Etudiant> etudiants = memberService.findByDiplomeOrderByDateInscriptionDesc(diplome);
			return new ResponseEntity<>(etudiants, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/etudiants/search")
	public ResponseEntity<List<Etudiant>> chercherEtudiants(@RequestParam String nom) {
		try {
			List<Etudiant> etudiants = memberService.chercherEtudiants(nom);
			return new ResponseEntity<>(etudiants, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Endpoints spécifiques pour EnseignantChercheur
	@GetMapping("/enseignants")
	public ResponseEntity<List<EnseignantChercheur>> getAllEnseignants() {
		try {
			List<EnseignantChercheur> enseignants = memberService.findAllEnseignants();
			return new ResponseEntity<>(enseignants, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/enseignants/{id}")
	public ResponseEntity<EnseignantChercheur> findEnseignant(@PathVariable Long id) {
		try {
			EnseignantChercheur enseignant = memberService.findEnseignant(id);
			if (enseignant != null) {
				return new ResponseEntity<>(enseignant, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/enseignants/grade/{grade}")
	public ResponseEntity<List<EnseignantChercheur>> findByGrade(@PathVariable String grade) {
		try {
			List<EnseignantChercheur> enseignants = memberService.findByGrade(grade);
			return new ResponseEntity<>(enseignants, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/enseignants/etablissement/{etablissement}")
	public ResponseEntity<List<EnseignantChercheur>> findByEtablissement(@PathVariable String etablissement) {
		try {
			List<EnseignantChercheur> enseignants = memberService.findByEtablissement(etablissement);
			return new ResponseEntity<>(enseignants, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Relation Etudiant-Enseignant
	@PostMapping("/affecter/{idetd}/{idens}")
	public ResponseEntity<Void> affecterEtudiantEnseignant(@PathVariable Long idetd, @PathVariable Long idens) {
		try {
			memberService.affecterEtudiantEnseignant(idetd, idens);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/etudiants-by-enseignant/{idens}")
	public ResponseEntity<List<Etudiant>> findEtudiantsByEnseignant(@PathVariable Long idens) {
		try {
			EnseignantChercheur ens = new EnseignantChercheur();
			ens.setId(idens);
			List<Etudiant> etudiants = memberService.findEtudiantsByEnseignant(ens);
			return new ResponseEntity<>(etudiants, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Recherche générale
	@GetMapping("/cin/{cin}")
	public ResponseEntity<Member> findByCin(@PathVariable String cin) {
		try {
			Member member = memberService.findByCin(cin);
			if (member != null) {
				return new ResponseEntity<>(member, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Member> findByEmail(@PathVariable String email) {
		try {
			Member member = memberService.findByEmail(email);
			if (member != null) {
				return new ResponseEntity<>(member, HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/nom/{nom}")
	public ResponseEntity<List<Member>> findByNom(@PathVariable String nom) {
		try {
			List<Member> members = memberService.findByNom(nom);
			return new ResponseEntity<>(members, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/nom-starts-with/{caractere}")
	public ResponseEntity<List<Member>> findByNomStartingWith(@PathVariable String caractere) {
		try {
			List<Member> members = memberService.findByNomStartingWith(caractere);
			return new ResponseEntity<>(members, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 /**
     * Affecter une publication à un auteur
     * POST /members/publications/affecter?idauteur=1&idpub=1
     */
    @PostMapping("/publications/affecter")
    public ResponseEntity<String> affecterPublicationToAuteur(
            @RequestParam Long idauteur, 
            @RequestParam Long idpub) {
        try {
            memberService.affecterAuteurToPublication(idauteur, idpub);
            return new ResponseEntity<>("Publication affectée avec succès", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Récupérer toutes les publications d'un auteur
     * GET /members/1/publications
     */
    @GetMapping("/{idauteur}/publications")
    public ResponseEntity<List<PublicationBean>> getPublicationsByAuteur(@PathVariable Long idauteur) {
        try {
            List<PublicationBean> publications = memberService.findPublicationParAuteur(idauteur);
            return new ResponseEntity<>(publications, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Récupérer un membre avec toutes ses publications (FullMember)
     * GET /members/fullmember/1
     */
    @GetMapping("/fullmember/{id}")
    public ResponseEntity<Member> findFullMember(@PathVariable Long id) {
        try {
            Member member = memberService.findMemberWithPublications(id);
            if (member != null) {
                return new ResponseEntity<>(member, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}