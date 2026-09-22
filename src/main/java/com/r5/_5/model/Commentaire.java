package com.r5._5.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "commentaire")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentaire_id")
    private Integer commentaireId;

    @Column(name = "contenu", length = 200)
    private String contenu;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "joueur_id", nullable = false)
    private Integer joueurId;

    public Commentaire() {
    }

    public Commentaire(Integer commentaireId, String contenu, LocalDate date, Integer joueurId) {
        this.commentaireId = commentaireId;
        this.contenu = contenu;
        this.date = date;
        this.joueurId = joueurId;
    }

    public Integer getCommentaireId() {
        return commentaireId;
    }

    public void setCommentaireId(Integer commentaireId) {
        this.commentaireId = commentaireId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getJoueurId() {
        return joueurId;
    }

    public void setJoueurId(Integer joueurId) {
        this.joueurId = joueurId;
    }
}