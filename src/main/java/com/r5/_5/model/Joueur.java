package com.r5._5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "joueur")
public class Joueur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "joueur_id")
    private Integer joueurId;

    @NotBlank
    @Size(min = 5, max = 5)
    @Column(name = "numero_licence", nullable = false, length = 5)
    private String numeroLicence;

    @Size(max = 50)
    @Column(name = "nom", length = 50)
    private String nom;

    @Size(max = 50)
    @Column(name = "prenom", length = 50)
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @DecimalMin("0.0")
    @DecimalMax("999.99")
    @Column(name = "taille", precision = 5, scale = 2)
    private BigDecimal taille;

    @DecimalMin("0.0")
    @DecimalMax("999.99")
    @Column(name = "poids", precision = 5, scale = 2)
    private BigDecimal poids;

    @Pattern(regexp = "ACTIF|BLESSE|SUSPENDU|ABSENT")
    @Size(max = 255)
    @Column(name = "statut")
    private String statut;

    public Joueur() {
    }

    public Joueur(Integer joueurId, String numeroLicence, String nom, String prenom, LocalDate dateNaissance,
            BigDecimal taille, BigDecimal poids, String statut) {
        this.joueurId = joueurId;
        this.numeroLicence = numeroLicence;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.taille = taille;
        this.poids = poids;
        this.statut = statut;
    }

    // Getters and setters

    public Integer getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(Integer joueurId) {
        this.joueurId = joueurId;
    }

    public String getNumeroLicence() {
        return numeroLicence;
    }

    public void setNumeroLicence(String numeroLicence) {
        this.numeroLicence = numeroLicence;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public BigDecimal getTaille() {
        return taille;
    }

    public void setTaille(BigDecimal taille) {
        this.taille = taille;
    }

    public BigDecimal getPoids() {
        return poids;
    }

    public void setPoids(BigDecimal poids) {
        this.poids = poids;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}