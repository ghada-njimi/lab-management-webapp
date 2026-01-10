package com.publication;

import java.text.SimpleDateFormat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.publication.dao.PublicationRepository;
import com.publication.entities.Publication;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
@EnableDiscoveryClient
public class PublicationServiceApplication implements CommandLineRunner {
    
    private final PublicationRepository publicationRepository;

    public static void main(String[] args) {
        SpringApplication.run(PublicationServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========== INITIALISATION DES PUBLICATIONS ==========\n");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Articles de journal
        Publication pub1 = Publication.builder()
                .type("article de journal")
                .titre("An approach for testing SOA systems")
                .dateApparition(sdf.parse("2023-01-15"))
                .lien("https://doi.org/10.1234/article1")
                .sourcePdf("articles/soa_testing_2023.pdf")
                .build();
        publicationRepository.save(pub1);
        
        Publication pub2 = Publication.builder()
                .type("article de journal")
                .titre("Machine Learning Applications in Software Engineering")
                .dateApparition(sdf.parse("2023-06-20"))
                .lien("https://doi.org/10.1234/article2")
                .sourcePdf("articles/ml_software_eng.pdf")
                .build();
        publicationRepository.save(pub2);
        
        Publication pub3 = Publication.builder()
                .type("article de journal")
                .titre("Cloud Computing Security Challenges")
                .dateApparition(sdf.parse("2024-02-10"))
                .lien("https://doi.org/10.1234/article3")
                .sourcePdf("articles/cloud_security.pdf")
                .build();
        publicationRepository.save(pub3);
        
        // Manifestations (Conférences)
        Publication pub4 = Publication.builder()
                .type("manifestation")
                .titre("International Conference on Software Architecture")
                .dateApparition(sdf.parse("2023-09-15"))
                .lien("https://conference.org/icsa2023")
                .sourcePdf("conferences/icsa_2023.pdf")
                .build();
        publicationRepository.save(pub4);
        
        Publication pub5 = Publication.builder()
                .type("manifestation")
                .titre("Workshop on Microservices Architecture")
                .dateApparition(sdf.parse("2024-03-25"))
                .lien("https://workshop.org/microservices")
                .sourcePdf("conferences/microservices_workshop.pdf")
                .build();
        publicationRepository.save(pub5);
        
        // Chapitres de livre
        Publication pub6 = Publication.builder()
                .type("chapitre de livre")
                .titre("Design Patterns for Distributed Systems")
                .dateApparition(sdf.parse("2023-04-10"))
                .lien("https://publisher.com/book/chapter5")
                .sourcePdf("books/chapters/design_patterns_ch5.pdf")
                .build();
        publicationRepository.save(pub6);
        
        Publication pub7 = Publication.builder()
                .type("chapitre de livre")
                .titre("Big Data Analytics: Fundamentals and Applications")
                .dateApparition(sdf.parse("2024-01-05"))
                .lien("https://publisher.com/book/chapter3")
                .sourcePdf("books/chapters/bigdata_ch3.pdf")
                .build();
        publicationRepository.save(pub7);
        
        // Livres
        Publication pub8 = Publication.builder()
                .type("livre")
                .titre("Modern Software Engineering Practices")
                .dateApparition(sdf.parse("2023-11-30"))
                .lien("https://publisher.com/books/modern-se")
                .sourcePdf("books/modern_se_practices.pdf")
                .build();
        publicationRepository.save(pub8);
        
        Publication pub9 = Publication.builder()
                .type("livre")
                .titre("Artificial Intelligence and Ethics")
                .dateApparition(sdf.parse("2024-05-15"))
                .lien("https://publisher.com/books/ai-ethics")
                .sourcePdf("books/ai_ethics.pdf")
                .build();
        publicationRepository.save(pub9);
        
        // Posters
        Publication pub10 = Publication.builder()
                .type("poster")
                .titre("Blockchain Technology in Healthcare")
                .dateApparition(sdf.parse("2023-08-20"))
                .lien("https://poster-session.org/blockchain-health")
                .sourcePdf("posters/blockchain_healthcare.pdf")
                .build();
        publicationRepository.save(pub10);
        
        Publication pub11 = Publication.builder()
                .type("poster")
                .titre("IoT Security Framework Proposal")
                .dateApparition(sdf.parse("2024-04-12"))
                .lien("https://poster-session.org/iot-security")
                .sourcePdf("posters/iot_security_framework.pdf")
                .build();
        publicationRepository.save(pub11);
        
        System.out.println("✓ " + publicationRepository.count() + " publications créées avec succès!");
        
        // Afficher quelques statistiques
        System.out.println("\n===== STATISTIQUES =====");
        System.out.println("Articles de journal : " + publicationRepository.countByType("article de journal"));
        System.out.println("Manifestations : " + publicationRepository.countByType("manifestation"));
        System.out.println("Chapitres de livre : " + publicationRepository.countByType("chapitre de livre"));
        System.out.println("Livres : " + publicationRepository.countByType("livre"));
        System.out.println("Posters : " + publicationRepository.countByType("poster"));
        
        System.out.println("\n========== APPLICATION PRÊTE ==========");
        System.out.println("API REST disponible sur : http://localhost:8092/api");
        System.out.println("Console H2 disponible sur : http://localhost:8092/h2-console");
        System.out.println("========================================\n");
    }
}