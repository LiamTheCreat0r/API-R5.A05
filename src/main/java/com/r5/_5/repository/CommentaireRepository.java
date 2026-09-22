package com.r5._5.repository;

import com.r5._5.model.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
    List<Commentaire> findByJoueurId(Integer joueurId);
}