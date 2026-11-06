package com.r5._5.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "participation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Integer participationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "joueur_id", nullable = false)
    private Joueur joueur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rencontre_id")
    private Rencontre rencontre;

    @Pattern(regexp = "TITULAIRE|REMPLACANT")
    @Column(name = "titulaire_ou_remplacant", length = 20)
    private String titulaireOuRemplacant;

    @Pattern(regexp = "TOPLANE|JUNGLE|MIDLANE|ADCARRY|SUPPORT")
    @Column(name = "poste", length = 20)
    private String poste;

    @Min(0)
    @Max(10)
    @Column(name = "note_performance")
    @JsonProperty("performance")
    private Integer notePerformance;

    public Participation() {
    }

    public Integer getParticipationId() {
        return participationId;
    }

    public void setParticipationId(Integer participationId) {
        this.participationId = participationId;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Rencontre getRencontre() {
        return rencontre;
    }

    public void setRencontre(Rencontre rencontre) {
        this.rencontre = rencontre;
    }

    public String getTitulaireOuRemplacant() {
        return titulaireOuRemplacant;
    }

    public void setTitulaireOuRemplacant(String titulaireOuRemplacant) {
        this.titulaireOuRemplacant = titulaireOuRemplacant;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Integer getNotePerformance() {
        return notePerformance;
    }

    public void setNotePerformance(Integer notePerformance) {
        this.notePerformance = notePerformance;
    }

}