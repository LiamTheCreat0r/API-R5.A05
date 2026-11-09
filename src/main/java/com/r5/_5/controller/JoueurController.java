package com.r5._5.controller;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.r5._5.model.Joueur;
import com.r5._5.repository.JoueurRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/joueurs")
public class JoueurController {

    private final JoueurRepository joueurRepository;
    private final Validator validator;

    public JoueurController(JoueurRepository joueurRepository, Validator validator) {
        this.joueurRepository = joueurRepository;
        this.validator = validator;
    }

    private Joueur findOrThrow(Integer id) {
        return joueurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Joueur not found: " + id));
    }

    @GetMapping
    public List<Joueur> getAll() {
        return joueurRepository.findAll();
    }

    @GetMapping("/{id}")
    public Joueur getOne(@PathVariable Integer id) {
        return findOrThrow(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Joueur create(@Valid @RequestBody Joueur joueur) {
        if (joueurRepository.existsByNumeroLicence(joueur.getNumeroLicence())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "numeroLicence already used");
        }
        joueur.setJoueurId(null); // force un INSERT, jamais une modification
        return joueurRepository.save(joueur);
    }

    @PutMapping("/{id}")
    public Joueur update(@PathVariable Integer id, @Valid @RequestBody Joueur updated) {
        Joueur joueur = findOrThrow(id);
        if (joueurRepository.existsByNumeroLicenceAndJoueurIdNot(updated.getNumeroLicence(), id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "numeroLicence already used");
        }
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
        Joueur joueur = findOrThrow(id);

        if (partial.getNumeroLicence() != null) {
            if (joueurRepository.existsByNumeroLicenceAndJoueurIdNot(partial.getNumeroLicence(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "numeroLicence already used");
            }
            joueur.setNumeroLicence(partial.getNumeroLicence());
        }
        if (partial.getNom() != null)
            joueur.setNom(partial.getNom());
        if (partial.getPrenom() != null)
            joueur.setPrenom(partial.getPrenom());
        if (partial.getDateNaissance() != null)
            joueur.setDateNaissance(partial.getDateNaissance());
        if (partial.getTaille() != null)
            joueur.setTaille(partial.getTaille());
        if (partial.getPoids() != null)
            joueur.setPoids(partial.getPoids());
        if (partial.getStatut() != null)
            joueur.setStatut(partial.getStatut());

        // valide le joueur après fusion (un PATCH partiel ne peut pas être @Valid)
        Set<ConstraintViolation<Joueur>> violations = validator.validate(joueur);
        if (!violations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .collect(java.util.stream.Collectors.joining(", ")));
        }
        return joueurRepository.save(joueur);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        if (!joueurRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Joueur not found: " + id);
        }
        try {
            joueurRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // joueur encore utilisé par des commentaires ou des participations
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Joueur still referenced");
        }
    }
}