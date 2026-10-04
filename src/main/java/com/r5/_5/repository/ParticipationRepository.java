package com.r5._5.repository;

import com.r5._5.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
    List<Participation> findByJoueur_JoueurId(Integer joueurId);

    List<Participation> findByRencontre_RencontreId(Integer rencontreId);
}