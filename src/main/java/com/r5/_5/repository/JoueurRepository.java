package com.r5._5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.r5._5.model.Joueur;

public interface JoueurRepository extends JpaRepository<Joueur, Integer> {
}