package com.member;

import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import com.member.beans.HalPublicationResponse;
import com.member.beans.PublicationBean;
import com.member.dao.EnseignantChercheurRepository;
import com.member.dao.EtudiantRepository;
import com.member.entities.EnseignantChercheur;
import com.member.entities.Etudiant;
import com.member.entities.Member;
import com.member.proxies.PublicationProxyService;
import com.member.services.IMemberService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@AllArgsConstructor
public class MemberServiceApplication implements CommandLineRunner {

	private final EnseignantChercheurRepository enseignantRepository;
	private final EtudiantRepository etudiantRepository;
	private final IMemberService memberService;
	private final PublicationProxyService publicationProxy;

	public static void main(String[] args) {
		SpringApplication.run(MemberServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║      INITIALISATION MEMBER SERVICE + FEIGN CLIENT        ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝\n");


		// ========== CRÉATION DES ENSEIGNANTS ==========
		System.out.println("═══ 1. CRÉATION DES ENSEIGNANTS ═══\n");

		EnseignantChercheur ens1 = EnseignantChercheur.builder().cin("15689").nom("Ben Ahmed").prenom("Mohamed")
				.grade("Professeur").date(new Date()).etablissement("ENIS").email("mohamed.benahmed@enis.tn")
				.password("pass123").cv("cv_mohamed.pdf").build();
		enseignantRepository.save(ens1);
		System.out.println("✓ Enseignant créé : " + ens1.getPrenom() + " " + ens1.getNom() + " (" + ens1.getGrade()
				+ " - " + ens1.getEtablissement() + ")");

		EnseignantChercheur ens2 = EnseignantChercheur.builder().cin("15690").nom("Trabelsi").prenom("Salma")
				.grade("Maître de conférences").date(new Date()).etablissement("FST").email("salma.trabelsi@fst.tn")
				.password("pass456").cv("cv_salma.pdf").build();
		enseignantRepository.save(ens2);
		System.out.println("✓ Enseignant créé : " + ens2.getPrenom() + " " + ens2.getNom() + " (" + ens2.getGrade()
				+ " - " + ens2.getEtablissement() + ")");

		// ========== CRÉATION DES ÉTUDIANTS ==========
		System.out.println("\n═══ 2. CRÉATION DES ÉTUDIANTS ═══\n");

		Etudiant etd1 = Etudiant.builder().cin("123456").nom("Yaakoubi").prenom("Fadoua").dateInscription(new Date())
				.date(new Date()).diplome("Mastère Recherche").email("fadoua.yaakoubi@gmail.com").password("etd123")
				.encadrant(null).cv("cv_fadoua.pdf").build();
		etudiantRepository.save(etd1);
		System.out.println(
				"✓ Étudiant créé : " + etd1.getPrenom() + " " + etd1.getNom() + " (" + etd1.getDiplome() + ")");

		Etudiant etd2 = Etudiant.builder().cin("123457").nom("Souissi").prenom("Wael").dateInscription(new Date())
				.date(new Date()).diplome("Doctorat").email("wael.souissi@gmail.com").password("etd456").encadrant(null)
				.cv("cv_wael.pdf").build();
		etudiantRepository.save(etd2);
		System.out.println(
				"✓ Étudiant créé : " + etd2.getPrenom() + " " + etd2.getNom() + " (" + etd2.getDiplome() + ")");

		Etudiant etd3 = Etudiant.builder().cin("123458").nom("Gharbi").prenom("Amine").dateInscription(new Date())
				.date(new Date()).diplome("Doctorat").email("amine.gharbi@gmail.com").password("etd789").encadrant(null)
				.cv("cv_amine.pdf").build();
		etudiantRepository.save(etd3);
		System.out.println(
				"✓ Étudiant créé : " + etd3.getPrenom() + " " + etd3.getNom() + " (" + etd3.getDiplome() + ")");

		// ========== AFFECTATION ÉTUDIANTS-ENSEIGNANTS ==========
		System.out.println("\n═══ 3. AFFECTATION ÉTUDIANTS AUX ENSEIGNANTS ═══\n");

		memberService.affecterEtudiantEnseignant(etd1.getId(), ens1.getId());
		System.out.println("✓ " + etd1.getPrenom() + " " + etd1.getNom() + " → Encadré par " + ens1.getPrenom() + " "
				+ ens1.getNom());

		memberService.affecterEtudiantEnseignant(etd2.getId(), ens2.getId());
		System.out.println("✓ " + etd2.getPrenom() + " " + etd2.getNom() + " → Encadré par " + ens2.getPrenom() + " "
				+ ens2.getNom());

		memberService.affecterEtudiantEnseignant(etd3.getId(), ens1.getId());
		System.out.println("✓ " + etd3.getPrenom() + " " + etd3.getNom() + " → Encadré par " + ens1.getPrenom() + " "
				+ ens1.getNom());

		// ========== TEST FEIGN CLIENT ==========
		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║              TEST OPENFEIGN CLIENT                        ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

		try {
			// Test 1: Récupérer une publication par ID
			System.out.println("═══ Test 1: Récupération d'une publication par ID ═══\n");
			PublicationBean pub1 = publicationProxy.findPublicationById(1L);
			if (pub1 != null) {
				System.out.println("✓ Publication ID=1 récupérée avec succès :");
				System.out.println("  • Titre    : " + pub1.getTitre());
				System.out.println("  • Type     : " + pub1.getType());
				System.out.println("  • Date     : " + pub1.getDateApparition());
				System.out.println("  • Lien     : " + pub1.getLien());
			} else {
				System.out.println("✗ Aucune publication trouvée avec l'ID 1");
			}

			// Test 2: Récupérer toutes les publications
			System.out.println("\n═══ Test 2: Liste de toutes les publications ═══\n");
			HalPublicationResponse response = publicationProxy.findAllPublications();
			List<PublicationBean> allPubs = response.getPublications();
			System.out.println("✓ " + allPubs.size() + " publications trouvées :\n");

			int count = 1;
			for (PublicationBean p : allPubs) {
				System.out.println("  " + count + ". " + p.getTitre() + " (" + p.getType() + ")");
				count++;
			}

			// Test 3: Affecter des publications aux membres
			System.out.println("\n═══ Test 3: Affectation publications → auteurs ═══\n");

			// Affecter les publications à l'enseignant 1
			System.out.println("Affectation à " + ens1.getPrenom() + " " + ens1.getNom() + " :");
			memberService.affecterAuteurToPublication(ens1.getId(), 1L);
			memberService.affecterAuteurToPublication(ens1.getId(), 2L);
			memberService.affecterAuteurToPublication(ens1.getId(), 3L);

			// Affecter les publications à l'enseignant 2
			System.out.println("\nAffectation à " + ens2.getPrenom() + " " + ens2.getNom() + " :");
			memberService.affecterAuteurToPublication(ens2.getId(), 4L);
			memberService.affecterAuteurToPublication(ens2.getId(), 5L);

			// Affecter une publication à l'étudiant 1
			System.out.println("\nAffectation à " + etd1.getPrenom() + " " + etd1.getNom() + " :");
			memberService.affecterAuteurToPublication(etd1.getId(), 6L);

			// Test 4: Récupérer les publications d'un auteur
			System.out.println("\n═══ Test 4: Publications par auteur ═══\n");

			System.out.println("📚 Publications de " + ens1.getPrenom() + " " + ens1.getNom() + " :");
			List<PublicationBean> pubsEns1 = memberService.findPublicationParAuteur(ens1.getId());
			if (pubsEns1.isEmpty()) {
				System.out.println("  Aucune publication trouvée");
			} else {
				for (PublicationBean pub : pubsEns1) {
					System.out.println("  • " + pub.getTitre() + " [" + pub.getType() + "]");
				}
			}

			System.out.println("\n📚 Publications de " + ens2.getPrenom() + " " + ens2.getNom() + " :");
			List<PublicationBean> pubsEns2 = memberService.findPublicationParAuteur(ens2.getId());
			if (pubsEns2.isEmpty()) {
				System.out.println("  Aucune publication trouvée");
			} else {
				for (PublicationBean pub : pubsEns2) {
					System.out.println("  • " + pub.getTitre() + " [" + pub.getType() + "]");
				}
			}

			System.out.println("\n📚 Publications de " + etd1.getPrenom() + " " + etd1.getNom() + " :");
			List<PublicationBean> pubsEtd1 = memberService.findPublicationParAuteur(etd1.getId());
			if (pubsEtd1.isEmpty()) {
				System.out.println("  Aucune publication trouvée");
			} else {
				for (PublicationBean pub : pubsEtd1) {
					System.out.println("  • " + pub.getTitre() + " [" + pub.getType() + "]");
				}
			}

			// Test 5: Récupérer un membre complet avec ses publications
			System.out.println("\n═══ Test 5: Full Member (Membre + Publications) ═══\n");

			Member fullMember = memberService.findMemberWithPublications(ens1.getId());
			System.out.println("👤 Membre complet : " + fullMember.getPrenom() + " " + fullMember.getNom());
			System.out.println("   Email : " + fullMember.getEmail());
			System.out.println(
					"   Nombre de publications : " + (fullMember.getPubs() != null ? fullMember.getPubs().size() : 0));

			if (fullMember.getPubs() != null && !fullMember.getPubs().isEmpty()) {
				System.out.println("\n   📚 Ses publications :");
				for (PublicationBean pub : fullMember.getPubs()) {
					System.out.println("      • " + pub.getTitre());
					System.out.println("        Type: " + pub.getType() + " | Date: " + pub.getDateApparition());
				}
			}

		} catch (Exception e) {
			System.err.println("\n✗ ERREUR lors du test Feign Client :");
			System.err.println("  Message: " + e.getMessage());
			System.err.println("  Assurez-vous que Publication-Service est démarré et enregistré dans Eureka");
			e.printStackTrace();
		}

		// ========== STATISTIQUES FINALES ==========
		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║                   STATISTIQUES FINALES                    ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

		System.out.println("📊 Total membres         : " + memberService.findAll().size());
		System.out.println("👨‍🏫 Total enseignants     : " + memberService.findAllEnseignants().size());
		System.out.println("👨‍🎓 Total étudiants       : " + memberService.findAllEtudiants().size());

		System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
		System.out.println("║           MEMBER SERVICE PRÊT POUR LES TESTS              ║");
		System.out.println("╚═══════════════════════════════════════════════════════════╝\n");

		System.out.println("🌐 API REST disponible sur :");
		System.out.println("   • Direct      : http://localhost:8091/members");
		System.out.println("   • Via Gateway : http://localhost:9000/member-service/members");
		System.out.println("   • Full Member : http://localhost:9000/member-service/members/fullmember/1");
		System.out.println();
	}
}