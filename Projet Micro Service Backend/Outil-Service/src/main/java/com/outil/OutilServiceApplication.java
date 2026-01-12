package com.outil;

import java.time.LocalDate; // IMPORTANT : Import de LocalDate

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.outil.dao.OutilRepository;
import com.outil.entities.Outil;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
@EnableDiscoveryClient
public class OutilServiceApplication implements CommandLineRunner {

    private final OutilRepository outilRepository;

    public static void main(String[] args) {
        SpringApplication.run(OutilServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n========== INITIALISATION DES OUTILS ==========\n");

        // Nettoyage optionnel pour éviter les doublons au redémarrage
        outilRepository.deleteAll();

        // Plus besoin de SimpleDateFormat, on utilise LocalDate.parse directement
        
        Outil outil1 = Outil.builder()
                .date(LocalDate.parse("2023-01-15"))
                .source("GitHub")
                .build();
        outilRepository.save(outil1);

        Outil outil2 = Outil.builder()
                .date(LocalDate.parse("2023-03-20"))
                .source("GitLab")
                .build();
        outilRepository.save(outil2);

        Outil outil3 = Outil.builder()
                .date(LocalDate.parse("2023-06-10"))
                .source("Docker Hub")
                .build();
        outilRepository.save(outil3);

        Outil outil4 = Outil.builder()
                .date(LocalDate.parse("2023-08-25"))
                .source("Jenkins")
                .build();
        outilRepository.save(outil4);

        Outil outil5 = Outil.builder()
                .date(LocalDate.parse("2023-11-05"))
                .source("SonarQube")
                .build();
        outilRepository.save(outil5);

        Outil outil6 = Outil.builder()
                .date(LocalDate.parse("2024-01-12"))
                .source("Jira")
                .build();
        outilRepository.save(outil6);

        Outil outil7 = Outil.builder()
                .date(LocalDate.parse("2024-03-18"))
                .source("Confluence")
                .build();
        outilRepository.save(outil7);

        Outil outil8 = Outil.builder()
                .date(LocalDate.parse("2024-05-22"))
                .source("Slack")
                .build();
        outilRepository.save(outil8);

        Outil outil9 = Outil.builder()
                .date(LocalDate.parse("2024-07-30"))
                .source("Trello")
                .build();
        outilRepository.save(outil9);

        Outil outil10 = Outil.builder()
                .date(LocalDate.parse("2024-10-14"))
                .source("Postman")
                .build();
        outilRepository.save(outil10);

        System.out.println("✓ " + outilRepository.count() + " outils créés avec succès!");

        // Statistiques
        System.out.println("\n===== STATISTIQUES =====");
        System.out.println("Total outils : " + outilRepository.count());
        
        // Attention : Assurez-vous que countBySource existe dans votre Repository (voir ci-dessous)
        try {
             System.out.println("Outils GitHub : " + outilRepository.countBySource("GitHub"));
        } catch (Exception e) {
             System.out.println("Note: Ajoutez 'long countBySource(String source);' dans OutilRepository pour voir ce chiffre.");
        }

        System.out.println("\n========== APPLICATION PRÊTE ==========");
        System.out.println("API REST disponible sur : http://localhost:8093/api"); // Vérifiez le port
        System.out.println("Console H2 disponible sur : http://localhost:8093/h2-console");
        System.out.println("========================================\n");
    }
}