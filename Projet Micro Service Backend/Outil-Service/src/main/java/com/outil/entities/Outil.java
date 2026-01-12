package com.outil.entities;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Outil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // LocalDate est parfait pour éviter les problèmes de décalage horaire (-1 jour)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String source;
}