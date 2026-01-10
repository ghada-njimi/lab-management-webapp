package com.evenement;

import java.text.SimpleDateFormat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.evenement.dao.EvenementRepository;
import com.evenement.entities.Evenement;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
@EnableDiscoveryClient
public class EvenementServiceApplication implements CommandLineRunner {
    
    private final EvenementRepository evenementRepository;

    public static void main(String[] args) {
        SpringApplication.run(EvenementServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========== INITIALISATION DES ÉVÉNEMENTS ==========\n");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // Conférences
        Evenement evt1 = Evenement.builder()
                .titre("International Conference on Software Engineering")
                .date(sdf.parse("2023-05-15"))
                .lieu("Paris, France")
                .build();
        evenementRepository.save(evt1);
        
        Evenement evt2 = Evenement.builder()
                .titre("Workshop on Artificial Intelligence")
                .date(sdf.parse("2023-07-20"))
                .lieu("Londres, UK")
                .build();
        evenementRepository.save(evt2);
        
        Evenement evt3 = Evenement.builder()
                .titre("Symposium on Cloud Computing")
                .date(sdf.parse("2023-09-10"))
                .lieu("Berlin, Allemagne")
                .build();
        evenementRepository.save(evt3);
        
        Evenement evt4 = Evenement.builder()
                .titre("Forum on Cybersecurity")
                .date(sdf.parse("2023-11-05"))
                .lieu("Bruxelles, Belgique")
                .build();
        evenementRepository.save(evt4);
        
        Evenement evt5 = Evenement.builder()
                .titre("Seminar on Data Science")
                .date(sdf.parse("2024-01-18"))
                .lieu("Amsterdam, Pays-Bas")
                .build();
        evenementRepository.save(evt5);
        
        Evenement evt6 = Evenement.builder()
                .titre("Conference on Machine Learning")
                .date(sdf.parse("2024-03-22"))
                .lieu("Zurich, Suisse")
                .build();
        evenementRepository.save(evt6);
        
        Evenement evt7 = Evenement.builder()
                .titre("Workshop on Blockchain Technology")
                .date(sdf.parse("2024-05-30"))
                .lieu("Dublin, Irlande")
                .build();
        evenementRepository.save(evt7);
        
        Evenement evt8 = Evenement.builder()
                .titre("International IoT Summit")
                .date(sdf.parse("2024-07-12"))
                .lieu("Copenhague, Danemark")
                .build();
        evenementRepository.save(evt8);
        
        Evenement evt9 = Evenement.builder()
                .titre("DevOps Conference")
                .date(sdf.parse("2024-09-25"))
                .lieu("Stockholm, Suède")
                .build();
        evenementRepository.save(evt9);
        
        Evenement evt10 = Evenement.builder()
                .titre("Big Data Analytics Forum")
                .date(sdf.parse("2024-11-08"))
                .lieu("Madrid, Espagne")
                .build();
        evenementRepository.save(evt10);
        
        // Événements futurs
        Evenement evt11 = Evenement.builder()
                .titre("Future Tech Summit 2025")
                .date(sdf.parse("2025-02-14"))
                .lieu("Tunis, Tunisie")
                .build();
        evenementRepository.save(evt11);
        
        Evenement evt12 = Evenement.builder()
                .titre("AI Innovation Conference")
                .date(sdf.parse("2025-04-20"))
                .lieu("Sfax, Tunisie")
                .build();
        evenementRepository.save(evt12);
        
        Evenement evt13 = Evenement.builder()
                .titre("Quantum Computing Workshop")
                .date(sdf.parse("2025-06-18"))
                .lieu("Sousse, Tunisie")
                .build();
        evenementRepository.save(evt13);
        
        System.out.println("✓ " + evenementRepository.count() + " événements créés avec succès!");
        
      
        System.out.println("Total événements : " + evenementRepository.count());
        System.out.println("Événements à Paris : " + evenementRepository.countByLieu("Paris, France"));
        System.out.println("Événements en Tunisie : " + evenementRepository.findByLieuContaining("Tunisie").size());
        System.out.println("Événements à venir : " + evenementRepository.findUpcomingEvents().size());
        System.out.println("Événements passés : " + evenementRepository.findPastEvents().size());
        
        System.out.println("\n===== PROCHAINS ÉVÉNEMENTS =====");
        evenementRepository.findUpcomingEvents().forEach(e -> {
            System.out.println("• " + e.getTitre() + " - " + e.getDate() + " - " + e.getLieu());
        });
   
        System.out.println("\n========== APPLICATION PRÊTE ==========");
        System.out.println("API REST disponible sur : http://localhost:8094/api");
        System.out.println("========================================\n");
    }
}