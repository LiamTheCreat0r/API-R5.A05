package com.r5._5.controller;

import com.r5._5.model.Rencontre;
import com.r5._5.repository.RencontreRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

import java.util.Set;

import java.util.List;

@RestController
@RequestMapping("/rencontres")
public class RencontreController {

    private final RencontreRepository rencontreRepository;

    private final Validator validator;

    public RencontreController(RencontreRepository rencontreRepository, Validator validator) {
        this.rencontreRepository = rencontreRepository;
        this.validator = validator;
    }

    @GetMapping
    public List<Rencontre> getAll() {
        return rencontreRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rencontre> getById(@PathVariable Integer id) {
        return rencontreRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rencontre create(@Valid @RequestBody Rencontre rencontre) {
        rencontre.setRencontreId(null); // force un INSERT, jamais une modification
        return rencontreRepository.save(rencontre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rencontre> update(@PathVariable Integer id, @Valid @RequestBody Rencontre body) {
        return rencontreRepository.findById(id)
                .map(existing -> {
                    existing.setDateHeure(body.getDateHeure());
                    existing.setEquipeAdverse(body.getEquipeAdverse());
                    existing.setAdresse(body.getAdresse());
                    existing.setLieu(body.getLieu());
                    existing.setResultat(body.getResultat());
                    return ResponseEntity.ok(rencontreRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Rencontre> patch(@PathVariable Integer id, @RequestBody Rencontre body) {
        return rencontreRepository.findById(id)
                .map(existing -> {
                    if (body.getDateHeure() != null) {
                        existing.setDateHeure(body.getDateHeure());
                    }
                    if (body.getEquipeAdverse() != null) {
                        existing.setEquipeAdverse(body.getEquipeAdverse());
                    }
                    if (body.getAdresse() != null) {
                        existing.setAdresse(body.getAdresse());
                    }
                    if (body.getLieu() != null) {
                        existing.setLieu(body.getLieu());
                    }
                    if (body.getResultat() != null) {
                        existing.setResultat(body.getResultat());
                    }
                    Set<ConstraintViolation<Rencontre>> violations = validator.validate(existing);
                    if (!violations.isEmpty()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, violations.stream()
                    .map(v -> v.getPropertyPath() + " " + v.getMessage())
                    .collect(java.util.stream.Collectors.joining(", ")));
                    }
                    return ResponseEntity.ok(rencontreRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!rencontreRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        try {
            rencontreRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            // rencontre encore utilisée par des participations
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}