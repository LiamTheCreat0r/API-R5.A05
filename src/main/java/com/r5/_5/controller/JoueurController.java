package com.r5._5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.r5._5.model.Joueur;
import com.r5._5.repository.JoueurRepository;

import java.util.List;

@RestController
@RequestMapping("/joueurs")
public class JoueurController {

    private final JoueurRepository joueurRepository;

    public JoueurController(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    @GetMapping
    public List<Joueur> getAll() {
        return joueurRepository.findAll();
    }

    @GetMapping("/{id}")
    public Joueur getOne(@PathVariable Integer id) {
        return joueurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Joueur not found: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Joueur create(@RequestBody Joueur joueur) {
        return joueurRepository.save(joueur);
    }

    @PutMapping("/{id}")
    public Joueur update(@PathVariable Integer id, @RequestBody Joueur updated) {
        Joueur joueur = joueurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joueur not found: " + id));
        joueur.setNumeroLicence(updated.getNumeroLicence());
        joueur.setNom(updated.getNom());
        joueur.setPrenom(updated.getPrenom());
        joueur.setDateNaissance(updated.getDateNaissance());
        joueur.setTaille(updated.getTaille());
        joueur.setPoids(updated.getPoids());
        joueur.setStatut(updated.getStatut());
        return joueurRepository.save(joueur);
    }

    @PatchMapping("/{id}")
    public Joueur patch(@PathVariable Integer id, @RequestBody Joueur partial) {
        Joueur joueur = joueurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Joueur not found: " + id));

        if (partial.getNumeroLicence() != null)
            joueur.setNumeroLicence(partial.getNumeroLicence());
        if (partial.getNom() != null)
            joueur.setNom(partial.getNom());
        if (partial.getPrenom() != null)
            joueur.setPrenom(partial.getPrenom());
        if (partial.getDateNaissance() != null)
            joueur.setDateNaissance(partial.getDateNaissance());
        if (partial.getTaille() != 0.0f)
            joueur.setTaille(partial.getTaille());
        if (partial.getPoids() != 0.0f)
            joueur.setPoids(partial.getPoids());
        if (partial.getStatut() != null)
            joueur.setStatut(partial.getStatut());

        return joueurRepository.save(joueur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        joueurRepository.deleteById(id);
    }
}