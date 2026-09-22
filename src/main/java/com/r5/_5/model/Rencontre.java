package com.r5._5.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rencontre")
public class Rencontre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rencontre_id")
    private Integer rencontreId;

    @Column(name = "date_heure")
    private LocalDateTime dateHeure;

    @Column(name = "equipe_adverse", length = 50)
    private String equipeAdverse;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "lieu", length = 20)
    private String lieu; // DOMICILE / EXTERIEUR

    @Column(name = "resultat", length = 20)
    private String resultat; // VICTOIRE / DEFAITE / NUL, null si pas jouée

    public Rencontre() {
    }

    public Rencontre(Integer rencontreId, LocalDateTime dateHeure, String equipeAdverse,
            String adresse, String lieu, String resultat) {
        this.rencontreId = rencontreId;
        this.dateHeure = dateHeure;
        this.equipeAdverse = equipeAdverse;
        this.adresse = adresse;
        this.lieu = lieu;
        this.resultat = resultat;
    }

    public Integer getRencontreId() {
        return rencontreId;
    }

    public void setRencontreId(Integer rencontreId) {
        this.rencontreId = rencontreId;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getEquipeAdverse() {
        return equipeAdverse;
    }

    public void setEquipeAdverse(String equipeAdverse) {
        this.equipeAdverse = equipeAdverse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
}